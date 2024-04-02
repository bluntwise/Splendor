
/**
 * Interface qui définit deux méthodes process et redéfinit toString
 *
 * @author: Clarence Bosser, Alan Dely, Enora Dussault, Mael Guillen
 * @version: 07/01/2024
 */
public interface Action
{
    public void process(Board b);
    public String toString();
}
