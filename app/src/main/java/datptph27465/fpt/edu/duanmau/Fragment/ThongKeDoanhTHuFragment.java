package datptph27465.fpt.edu.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import datptph27465.fpt.edu.duanmau.R;
import datptph27465.fpt.edu.duanmau.dao.PhieuMuonDao;


public class ThongKeDoanhTHuFragment extends Fragment {

    private EditText etFromDate, etToDate;
    private TextView tvRevenue;
    private Button btnRevenue;
    private SimpleDateFormat sdf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_doanh_t_hu, container, false);

        etFromDate = view.findViewById(R.id.etFromDate);
        etToDate = view.findViewById(R.id.etToDate);
        tvRevenue = view.findViewById(R.id.tvRevenue);
        btnRevenue = view.findViewById(R.id.btnRevenue);

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etFromDate);
            }
        });

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(etToDate);
            }
        });

        btnRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý tính doanh thu
                tvRevenue.setText("Doanh Thu: " + calculateRevenue());
            }
        });

        return view;
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        editText.setText(sdf.format(calendar.getTime()));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private String calculateRevenue() {
        String fromDate = etFromDate.getText().toString();
        String toDate = etToDate.getText().toString();

        if (fromDate.isEmpty() || toDate.isEmpty()) {
            return "Vui lòng chọn cả 2 ngày!";
        }

        try {
            Calendar fromCalendar = Calendar.getInstance();
            Calendar toCalendar = Calendar.getInstance();

            fromCalendar.setTime(sdf.parse(fromDate)); // Chuyển từ string sang Date
            toCalendar.setTime(sdf.parse(toDate));     // Chuyển từ string sang Date

            SimpleDateFormat dbDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String fromDateString = dbDateFormat.format(fromCalendar.getTime());
            String toDateString = dbDateFormat.format(toCalendar.getTime());
            Log.d("checkqqqqq", "fromDateString: "+fromDateString);
            Log.d("checkqqqqq", "toDateString: "+toDateString);
            PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
            double totalRevenue = phieuMuonDao.getDoanhThuTheoKhoangNgay(fromDateString, toDateString);

            return  totalRevenue + " VND";
        } catch (Exception e) {
            e.printStackTrace();
            return "Lỗi khi tính toán doanh thu!";
        }
    }

}