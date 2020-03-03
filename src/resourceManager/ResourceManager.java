package resourceManager;

/**
 * A resource manger manages a single {@link Resource}, which may be used by any number of {@link ResourceUser}s.
 * <p>
 * Only one resource user may access the resource at any one time.  Resource users request access to the
 * resource with a given priority.  If the resource is currently in use, the resource user must wait until the resource
 * is no longer in use.  When a resource user releases the resource the resource manager will, if there are any
 * resource users currently waiting to access the resource, determine what the highest priority is at which resource
 * users are waiting and allow one of these highest priority users to access the resource.
 * </p>
 * <p>
 * A resource can only be used a certain number of times, which is determined when its ResourceManager is
 * constructed.  Once the resource has been used this number of times it is exhausted, and may not be used any more.
 * </p>
 * <p>
 * All methods specified in this interface are implemented in {@link BasicResourceManager}, <i>with the exception of
 * {@link #requestResource(int)}} and {@link #releaseResource()}.</i>
 *
 * @author Hugh Osborne
 * @version February 2020
 */

public interface ResourceManager
{
    /**
     * Generate a random priority in the range permitted by this resource manager.
     * @return a random priority from the interval [0,MAX_PRIORITY].
     */    
    public int getRandomPriority();
    
    /**
     * Get the name of the resource managed by this resource manager.
     * @return the name of the resource managed by this resource manager.
     */
    public String getResourceName();
    
    /**
     * Check whether this manager's resource is exhausted.
     * @return true iff the resource is exhausted.
     */
    public boolean resourceIsExhausted();
    
    /**
     * Request use of this manager's resource, with the specified priority.
     * If the resource is in use the requesting user will have to wait for the resource to be released.
     * @param priority the priority level at which the resource is being requested.
     * @throws ResourceError if the implementing code throws an InterruptedException error.
     */
    public void requestResource(int priority) throws ResourceError;
    
    /**
     * Allow the resource to be used for a specified length of time.
     * Calls of this method <i>must</i> be protected by (properly implemented) calls of
     * requestResource() and releaseResource().
     * @param timeRequired the time, in milliseconds, for which the requesting user requires use of the resource.
     * @throws ResourceError if more than one user is using the resource, or if the resource is exhausted.
     */
    public void useResource(int timeRequired) throws ResourceError;
    
    /**
     * Release this manager's resource.  If any users are waiting for the resource a waiting user with the
     * highest priority should be woken.
     * @return the priority level of the woken process if such exists, NONE_WAITING if not.
     * @throws ResourceError if the implementing code throws an InterruptedException error.
     */
    public int releaseResource() throws ResourceError;
}
