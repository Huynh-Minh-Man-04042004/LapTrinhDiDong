package com.example.baitap04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rvSongs;
    private SongAdapter mSongAdapter;
    private List<SongModel> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rvSongs = findViewById(R.id.rv_songs);

        // Tạo danh sách bài hát
        mSongs = new ArrayList<>();
        mSongs.add(new SongModel("60696", "IF YOU STILL EXIST", "When I start a love, That's when I change myself", "Trinh Dinh Quang"));
        mSongs.add(new SongModel("60701", "NGỐC", "There are many stories that I keep hidden, only I know", "Khắc Việt"));
        mSongs.add(new SongModel("60650", "Trust Me One More Time", "Even though we were wrong when we were together, my love", "Thien Dung"));
        mSongs.add(new SongModel("60610", "A SERIES OF DAYS WITHOUT YOU", "Since you left, my heart has been filled with sadness", "Duy Cuong"));
        mSongs.add(new SongModel("60656", "WHEN THE ONE YOU LOVE CRY", "My tears are falling on my fingers My tears", "Pham Manh Quynh"));
        mSongs.add(new SongModel("60685", "OPEN", "I dream of meeting you, I dream of hugging you, I dream of being close to you", "Trinh Thang Binh"));
        mSongs.add(new SongModel("60752", "PATCHED LOVE", "Want to go far away from the love I once had So I won't hear it", "Mr. Siro"));
        mSongs.add(new SongModel("60608", "WAITING FOR THE RAIN TO CLOSE", "A rainy day and you're far away where my shadow remains", "Trung Duc"));
        mSongs.add(new SongModel("60603", "THE QUESTION YOU HAVEN'T ANSWER", "I need a sincere explanation from you, Don't be silent", "Yuki Huy Nam"));
        mSongs.add(new SongModel("60720", "QUIETLY PASSING AWAY", "Sometimes when we come together, love doesn't last long but when", "Phan Manh Quynh"));
        mSongs.add(new SongModel("60856", "I CAN'T FORGET YOU - REMIX", "How long does it take for me to forget the pain? Need more", "Thien Ngon"));
        mSongs.add(new SongModel("60599", "BROKEN DREAMS", "Every night I dream of you but wake up alone", "Minh Vuong M4U"));
        mSongs.add(new SongModel("60789", "THE LAST GOODBYE", "Our memories are now just a distant past", "Nguyen Dinh Vu"));
        mSongs.add(new SongModel("60901", "A PROMISE TO REMEMBER", "No matter where you are, I still keep our promise", "Ho Quang Hieu"));
        mSongs.add(new SongModel("60873", "LOST IN LOVE", "I thought we would last forever, but fate was cruel", "Bui Anh Tuan"));
        mSongs.add(new SongModel("60745", "FADED LOVE", "The love we once had is now just a fading memory", "Erik"));
        mSongs.add(new SongModel("60899", "FOREVER YOU AND ME", "No matter what happens, I will always be by your side", "Jack J97"));

        // Tạo Adapter và gán vào RecyclerView
        mSongAdapter = new SongAdapter(this, mSongs);
        rvSongs.setAdapter(mSongAdapter);

        // Đặt LayoutManager cho RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSongs.setLayoutManager(linearLayoutManager);
    }
}
