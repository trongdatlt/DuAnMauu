package datptph27465.fpt.edu.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import datptph27465.fpt.edu.duanmau.Adapter.TopSachAdapter;
import datptph27465.fpt.edu.duanmau.Models.TopSach;
import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.PhieuMuonDao;


public class Top10DanhSachFragment extends Fragment {

    private RecyclerView recyclerViewTop10Books;
    private TopSachAdapter adapter;
    private List<TopSach> topSachList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10_danh_sach, container, false);

        recyclerViewTop10Books = view.findViewById(R.id.recyclerViewTop10Books);
        recyclerViewTop10Books.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy dữ liệu từ cơ sở dữ liệu
        topSachList = getTop10Books(); // Đây là phương thức giả định bạn đã có

        adapter = new TopSachAdapter(topSachList);
        recyclerViewTop10Books.setAdapter(adapter);

        return view;
    }

    private List<TopSach> getTop10Books() {
        List<TopSach> list = new ArrayList<>();
        datptph27465.fpt.edu.duanmau.dao.PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
        list.addAll(phieuMuonDao.getTop10Sachs());
        return list;
    }
}
