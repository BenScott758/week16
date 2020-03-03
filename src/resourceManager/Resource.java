package resourceManager;

/**
 * Models a resource, that will be used by {@link ResourceUser}s, and managed by a {@link ResourceManager}.
 * A resource can be used by any number of resource users.  The resource's resource manager ensures that no more than
 * one resource user may access the resource at any one time.  Resource users request access to the
 * resource with a given priority.  If the resource is currently in use, the resource user must wait until the resource
 * is no longer in use.  When a resource user releases the resource the resource manager will, if there are any
 * resource users currently waiting to access the resource, determine what the highest priority is at which resource
 * users are waiting and allow one of these highest priority users to access the resource.
 * <p>
 * A resource can only be used a certain number of times, which is determined when its ResourceManager is
 * constructed.(see {@link BasicResourceManager#BasicResourceManager(Resource, int)}.)
 * Once the resource has been used this number of times it is exhausted, and may not be used any more.
 * </p>
 * @author Hugh Osborne
 * @version February 2020
 */

public class Resource
{
    /**
     * This resource's name.
     */
    private String name;
    
    /**
     * Set the resource's name and determine the number of times it can be used.
     * @param name the resource's name
     * The actual number of useages will be a random value in the range (0,maxUseagesToLive].
     */
    public Resource(String name) {
        this.name = name;
    }
    
    /**
     * Get the resource's name
     * @return the resource's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Construct a string containing information about this resource. 
     * @return A string containing the resource's name
     */
    public String toString() {
        return "resource \"" + getName() + "\"";
    }

    /**
     * Use this resource, and print information about its use.
     * @param timeRequired the time, in milliseconds, for which the user wants to use the resource
     */
    public void use(int timeRequired) {
        Thread user = Thread.currentThread();
        System.out.println(user.getName() + " is using " + this);
        try {
            Thread.sleep(timeRequired);
        } catch (InterruptedException ie) {
            // not bothered if its interrupted
        }
        System.out.println(user.getName() + " has finished using " + this);
    }
}
