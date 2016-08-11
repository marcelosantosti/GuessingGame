# GuessingGame
Implementation of a guessing game that figures out what the user thinks based on his inputs. The project was developed specifically to Android platform.

# Description
This is a simple guessing game project that discover what the user is thinking based on previous inputs. The game works similarly to Akinator (http://en.akinator.com/), however, it works just offline, without a cloud base. Besides that, the project is using an architecture based on DDD from Martin Fowler that was modified according to the project's requirements.

### Frameworks
- AndroidAnnotations: View Injection and background processing 
- RoboGuice: Dependency Injection
- ORMLite: Offline storage. Layer to abstract SQLite

###To Do's
- Change the architecture to use a tree decision data structure
