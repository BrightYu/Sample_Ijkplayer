/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bright.ijkplayer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bright.ijkplayer.R;
import com.bright.ijkplayer.utils.ScreenOrientationUtils;
import com.bright.ijkplayer.widget.MediaController;
import com.bright.ijkplayer.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = VideoActivity.class.getSimpleName();

    private MediaController mMediaController;
    private IjkVideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // init UI
        mMediaController = new MediaController(this);
        mMediaController.setTitle("变形金刚2");
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mVideoView = (IjkVideoView) findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
        // prefer mVideoPath
        mVideoView.setVideoPath("http://mss.pinet.co/index.php/api/retrieve/3da4edce-b445-42c8-88a7-3b8a1997d61c/playlist.m3u8");
        //mVideoView.setVideoPath("http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear4/prog_index.m3u8");

        mVideoView.start();

    }

    @Override
    public void onBackPressed() {
        if (ScreenOrientationUtils.isLandscape(this)) {
            mMediaController.changePortrait();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoView.canPause()) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        mVideoView.stopPlayback();
        mVideoView.release(true);
        IjkMediaPlayer.native_profileEnd();
        super.onDestroy();
    }
}
