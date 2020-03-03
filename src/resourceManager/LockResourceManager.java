package resourceManager;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * A BasicResourceManager implementation which utilizes the Locks and Conditions to control access
 * to implement the requestResource(int priority) and releaseResource()methods
 * @author benscott
 * @version March 2020
 */
public class LockResourceManager extends BasicResourceManager
{
    private final Lock _resourceLock = new ReentrantLock();	//The lock which is able to provide atomic access to the object's .
    private final Condition[] _conditions = new Condition[NO_OF_PRIORITIES]; // This is an array of _conditions which represents the different priorities which is in waiting.
    private boolean _resourceInUse = false; // If the resource is not in use(false) then no waiting is needed.
    /**
     * Each priority has its various _conditions initialised by the constructor. The constructor sets the resources which are to be utilised and the number of uses which can be provided all together.
     * @param resource the resource which gets control access to.
     * @param maxUses the number of uses which can be provided all together.
     */

    public LockResourceManager(Resource resource, int maxUses)
    {
        super(resource, maxUses);

        for (int i = 0; i < NO_OF_PRIORITIES; i++)
            _conditions[i] = _resourceLock.newCondition();
    }

    /**
     * This requests access to the resource which possesses a given priority. If the resource is currently being used then the thread
     * will sleep unit it reaches the highest priority request and a subsequent thread has released the resource. If not the resource will be provided.
     *
     * @param priority This request's priority is from 0 which is (inclusive) to N0_OF_PRIORITIES (exclusive)
     */

    @Override
    public void requestResource(int priority) throws ResourceError
    {
        _resourceLock.lock(); //This locks the object so that no other threads are allowed to enter until the current thread has come back or is currently waiting.
        try
        {
            if (_resourceInUse)
            {
                increaseNumberWaiting(priority); // this increases the amount of threads waiting at priority
                _conditions[priority].await();
                decreaseNumberWaiting(priority);
            }

            _resourceInUse = true; // indication to show that this thread is using the resource.
        }
        catch (InterruptedException ie)
        {
            System.out.println(ie.getClass().getName() + ": " + ie.getMessage());
        }
        finally
        {
            _resourceLock.unlock(); //This allows the method to be unlocked when execution leaves the try section.
        }
    }


    /**
     * This allows thee resource to get access released after it has been used.
     * If there are threads which are waiting it allows the one with the highest priority to be signaled.
     *
     * @return the thread priority which has been signlaed or NONE_WAITING if no other threads presently waiting.
     */
    @Override
    public int releaseResource() throws ResourceError
    {
        _resourceLock.lock();
        try
        {
            _resourceInUse = false;



			int priority = NO_OF_PRIORITIES-1; // this begins at the highest priority
			for (;
				priority >= 0 && getNumberWaiting(priority) <= 0;
				priority--);//Decreases the increment of  the priority to consider the next highest.

            if (priority < 0) //If below 0 then no thread was waiting.
                return NONE_WAITING;

            _conditions[priority].signal(); // this signals a thread which has the highest priority with the threads which are waiting.
            return priority;
        }
        finally
        {
            _resourceLock.unlock();
        }
    }
}
