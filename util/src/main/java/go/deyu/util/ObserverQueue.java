package go.deyu.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import go.deyu.util.exception.QueueEmptyException;


/**
 * Created by huangeyu on 15/8/31.
 */
public class ObserverQueue<E> {

    private int number = 0;
    private Queue<E> mQueue;
    private E mNowElement = null;
    private List<OnNextListener<E>> mListeners ;

    public ObserverQueue(Collection<E> elements){
        mListeners = new ArrayList<OnNextListener<E>>();
        mQueue = new LinkedList<E>();
        mQueue.addAll(elements);
    }

    public void next() throws QueueEmptyException {
        if(mQueue.isEmpty())throw new QueueEmptyException();
        mNowElement = mQueue.poll();
        onNext(mNowElement);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void reset(){
        setNumber(0);
    }

    public E getLastPullElement(){
        return mNowElement;
    }

    private void onNext(E e){
        number++;
        for(OnNextListener l : mListeners){
            l.OnNext(e);
        }
    }

    public void registerOnNextListener(@NonNull OnNextListener listener){
        mListeners.add(listener);
    }

    public void unregisterOnNextListener(@NonNull OnNextListener listener){
        if(mListeners.indexOf(listener)==-1)return;
        mListeners.remove(listener);
    }

    public interface OnNextListener<E>{
        public void OnNext(E mNextElement);
    }
}
