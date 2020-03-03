package resourceManager;


/**
 * Demonstrates the use of {@link Resource}s, {@link ResourceManager}s and {@link ResourceUser}s in a
 * {@link ResourceSystem}.
 *
 * @author  Hugh Osborne
 * @version February 2020
 */
public class ResourceSystemMain
{
    /**
     * Runs a resource system with four users sharing one resource.
	 * @param args not used.
	 * @throws ResourceError if there is an error while running the resource system.
     * See {@link ResourceSystem#run}
     */
	public static void main(String[] args) throws ResourceError
	{
		ResourceSystem resourceSystem = new ResourceSystem();
		resourceSystem.addResource("A", 50); // The resource - may be used up to 20 times
		resourceSystem.addUser("1",0.1); // User 1 uses the resource for up to 1/10 second each time
		resourceSystem.addUser("2",0.1); // User 2 uses the resource for up to 1/10 second each time
		resourceSystem.addUser("3",0.2); // User 3 uses the resource for up to 1/5 second each time
		resourceSystem.addUser("4",0.2); // User 4 uses the resource for up to 1/5 second each time
		resourceSystem.addUser("5",0.2); // User 5 uses the resource for up to 1/5 second each time
		resourceSystem.addUser("6",0.2); // User 6 uses the resource for up to 1/5 second each time
		resourceSystem.addUser("7",1); // User 7 uses the resource for up to 1/5 second each time
		resourceSystem.addUser("8",2); // User 8 uses the resource for up to 1/5 second each time

		resourceSystem.run();
	}
}

