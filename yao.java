@OnClick(R.id.btn_capture)
void capture() {
    mVisualiserActivity = null;
    if (mRealTime) {
        inProgress();
        c = mNotchService.capture(new NotchCallback<Void>() {
            @Override
            public void onProgress(NotchProgress progress) {
                if (progress.getState() == NotchProgress.State.REALTIME_UPDATE) {
                    mRealTimeData = (Data) progress.getObject();
                    //mRealTimeData.
                    Skeleton tempSkel=        mRealTimeData.getSkeleton();
                    Bone newBone= tempSkel.getBone("RightApperArm");
                    mSocket.emit("newmessage", mRealTimeData);
                    updateRealTime();
                }
            }

            @Override
            public void onSuccess(Void nothing) {
                clearText();
            }

            @Override
            public void onFailure(NotchError notchError) {
                Util.showNotification(Util.getNotchErrorStr(notchError));
                clearText();
            }

            @Override
            public void onCancelled() {
                Util.showNotification("Real-time measurement stopped!");
                clearText();
            }
        });
    }
    else {
        mNotchService.timedCapture(new EmptyCallback<Measurement>() {
            @Override
            public void onSuccess(Measurement measurement) {
                mCurrentMeasurement = measurement;
                Map<String, List<Bone>> status = measurement.getStatus();
                //////
                mSocket.emit("newmessage", mCurrentMeasurement);
                mSocket.emit("newmessage", status);
                //mSocket.emit("newmessage", Uri.fromFile(mCurrentOutput));
                //////
                Util.showNotification("Capture finished");
                clearText();
            }
        });
    }
}