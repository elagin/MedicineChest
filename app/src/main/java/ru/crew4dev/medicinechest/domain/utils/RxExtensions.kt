package ru.crew4dev.medicinechest.domain.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//subscribe

fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.schedulersIoToIo(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}

fun <T> Single<T>.schedulersComputationToMain(): Single<T> =
    subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.schedulersIoToMain(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.schedulersIoToMain(): Flowable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersIoToMain(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersComputationToMain(): Completable =
    subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

fun Completable.schedulersIoToIo(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
}

//check dispose

fun <T> SingleEmitter<T>.safeOnSuccess(value: T) {
    if (!this.isDisposed) {
        this.onSuccess(value)
    }
}

fun <T> SingleEmitter<T>.safeOnError(th: Throwable) {
    if (!this.isDisposed) {
        this.onError(th)
    }
}

fun CompletableEmitter.safeComplete() {
    if (!this.isDisposed) {
        this.onComplete()
    }
}

fun CompletableEmitter.safeOnError(th: Throwable) {
    if (!this.isDisposed) {
        this.onError(th)
    }
}