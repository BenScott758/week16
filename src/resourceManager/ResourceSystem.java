package resourceManager;
import java.util.Set;
import java.util.HashSet;

/**
 * A resource system consists of a number of {@link ResourceManager}s, each managing an individual {@link Resource},
 * and a number of {@link ResourceUser}s, who wish to make use of the resources.
 * <p>
 * Resource users may request access to a resource through the resource's resource manager, but only one resource
 * user is allowed access to a given resource at any one time. If the resource is in use the requesting resource
 * user must wait until the resource becomes available again.  Access requests are made with a given priority.  When
 * a resource becomes available by a resource user releasing the resource the resource manager will determine if there
 * are any processes waiting for access.  If there are, the resource manager must determine the highest priority of all
 * pending access requests, and only allow access to one of the resource users who made one of these highest priority
 * requests.
 * </p>
 * <p>
 * Note that the priorities are associated with the access requests, not with the resource users.  One resource user
 * may, make a number of access requests at different times with differing priorities.
 * </p>
 * <p>
 * Note also that there is currently an error in the code in {@link #addResource(String, int)}, the implementation of
 * which requires a full implementation of the {@link ResourceManager} interface to be available, which is not
 * available in the bundle as provided.
 * </p>
 *
 * @author Hugh Osborne
 * @version February 2020
 */

public class ResourceSystem
{
    /**
     * The set of managers managing the resources to be used by the resource users.
     * Since each resource manager manages a single resource this also implicitly defines the set of resources in
     * the system.
     */
    private Set<ResourceManager> managers;
    /**
     * The set of resource users in the system.
     */
    private Set<ResourceUser> users;
    
    /**
     * Initialise the resource managers and resource users sets.
     */
    public ResourceSystem() {
        managers = new HashSet<ResourceManager>();
        users = new HashSet<ResourceUser>();
    }
    
    /**
     * Add a new resource by creating one, with the appropriate manager.
     * <p>
     * <b>Note</b>: when you have a full implementation of {@link ResourceManager} (e.g. in a
     * <tt>PriorityResourceManager</tt> class) you should replace the erroneous call of the (non-existent)
     * <tt>ResourceManager</tt> constructor with a call of the corresponding constructor from your class.
     * </p>
     * @param name the name of the resource to be added.
     * @param maxUseages the maximum number of times this resource can be used (the actual number may
     * be lower - see {@link BasicResourceManager#BasicResourceManager(Resource, int)}.)
     */
    public void addResource(String name,int maxUseages) {
        managers.add(new LockResourceManager(new Resource(name),maxUseages));
    }
    
    /**
     * Add a new resource user.  The resource user may make use of all the resources created.
     * @param name the name of the resource user.
     * @param maxDelay the maximum time, in seconds, that the resource user will ever use any resource.
     */
    public void addUser(String name,double maxDelay) {
        users.add(new ResourceUser(name,maxDelay,managers));
    }
    
    /**
     * Run this resource user.  Each resource user will run until all the resources at its disposal are exhausted.
     * @throws ResourceError if there is an InterruptedException while the system waits to <tt>join()</tt> the resource users.
     */
    public void run() throws ResourceError {
        for (ResourceUser user: users) {
        	System.out.println("Starting " + user);
            user.start();
        }
        try {
            for (ResourceUser user: users) {
                user.join();
                System.out.println(user + " has finished");
            }
        } catch (InterruptedException ie) {
            throw new ResourceError("The system was interrupted while waiting for the resource users to terminate.\n" + ie.getMessage());
        }
        System.out.println("All processes finished");
    }
}
