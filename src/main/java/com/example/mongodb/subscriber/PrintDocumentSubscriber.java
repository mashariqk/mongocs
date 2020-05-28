package com.example.mongodb.subscriber;

import com.mongodb.client.model.changestream.ChangeStreamDocument;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.reactivestreams.Subscriber;
import org.bson.Document;

public class PrintDocumentSubscriber implements Subscriber<ChangeStreamDocument<Document>> {
    /**
     * Invoked after calling {@link Publisher#subscribe(Subscriber)}.
     * <p>
     * No data will start flowing until {@link Subscription#request(long)} is invoked.
     * <p>
     * It is the responsibility of this {@link Subscriber} instance to call {@link Subscription#request(long)} whenever more data is wanted.
     * <p>
     * The {@link Publisher} will send notifications only in response to {@link Subscription#request(long)}.
     *
     * @param s {@link Subscription} that allows requesting data via {@link Subscription#request(long)}
     */
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    /**
     * Data notification sent by the {@link Publisher} in response to requests to {@link Subscription#request(long)}.
     *
     * @param documentChangeStreamDocument the element signaled
     */
    @Override
    public void onNext(ChangeStreamDocument<Document> documentChangeStreamDocument) {
        System.out.println(String.format("\nonNext: %s\n",documentChangeStreamDocument));
    }

    /**
     * Failed terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     *
     * @param t the throwable signaled
     */
    @Override
    public void onError(Throwable t) {
        System.out.println("Error "+t);
    }

    /**
     * Successful terminal state.
     * <p>
     * No further events will be sent even if {@link Subscription#request(long)} is invoked again.
     */
    @Override
    public void onComplete() {
        System.out.println("\n\n\nCOMPLETED!!!!!\n\n\n");
    }
}
