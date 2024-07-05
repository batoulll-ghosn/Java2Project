import java.util.Observable;
import java.util.Observer;

public abstract class ObservableEntity extends Observable {
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        super.deleteObserver(observer);
    }

    public void notifyObservers(Object obj) {
        super.notifyObservers(obj);
    }
}
