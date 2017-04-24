Here is the code: paste it in the MainFragment, replacing the capture() function. 

Cancellable c;
long mUpdateStartTime;
long mRefreshTime = 20;

@OnClick(R.id.btn_capture)
void capture() {
    mVisualiserActivity = null;
    if (mRealTime) {
        inProgress();
        c = mNotchService.capture(new NotchCallback<Void>() {
            @Override
            public void onProgress(NotchProgress progress) {
                if (progress.getState() == NotchProgress.State.REALTIME_UPDATE) {
                    mRealTimeData = (VisualiserData) progress.getObject();
                    //updateRealTime();
                    mUpdateStartTime = System.currentTimeMillis();
                    mHandler.removeCallbacks(mLogRealTimeData);
                    mHandler.post(mLogRealTimeData);
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
                mHandler.removeCallbacks(mLogRealTimeData);
            }

            @Override
            public void onCancelled() {
                Util.showNotification("Real-time measurement stopped!");
                clearText();
                mHandler.removeCallbacks(mLogRealTimeData);

            }
        });
    }
    else {
        mNotchService.timedCapture(new EmptyCallback<Measurement>() {
            @Override
            public void onSuccess(Measurement measurement) {
                mCurrentMeasurement = measurement;
                Util.showNotification("Capture finished");
                clearText();
            }
        });
    }
}

Runnable mLogRealTimeData = new Runnable() {
    @Override
    public void run() {
        // Index of first new frame in the last update
        int startingFrame = mRealTimeData.getStartingFrame();
        // Elapsed time since the last update
        long millisSinceUpdate = System.currentTimeMillis() - mUpdateStartTime;
        // Recording frequency
        float frequency = mRealTimeData.getFrequency();
        // Milliseconds per frame
        float millisPerFrame = 1000.f / frequency;
        // Slect the current frame from the last update
        int currentFrame = startingFrame + (int)(millisSinceUpdate / millisPerFrame);

        // Show the last frame until a new update comes
        if (currentFrame > mRealTimeData.getFrameCount() - 1) currentFrame = mRealTimeData.getFrameCount() - 1;

        // Logging data for measured bones
        Log.d("REALTIME", "Current frame:" + currentFrame);
        for (Bone b : mNotchService.getNetwork().getDevices().keySet()) {
            Log.d("REALTIME", b.getName() + " "
                    // Orientation (quaternion)
                    + mRealTimeData.getQ(b,currentFrame) + " "
                    // Position of the bone (end of vector)
                    + mRealTimeData.getPos(b,currentFrame));
        }

        mHandler.postDelayed(mLogRealTimeData,mRefreshTime);
    }
};