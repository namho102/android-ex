package example.timepicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    EditText etTitle, etDesciption;
    TextView tvDate, tvTime;
    Button btnDate, btnTime, btnAddJob;
    ListView lvJob;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;
    ArrayList<JobInWeek> arrJob=new ArrayList<JobInWeek>();
    ArrayAdapter<JobInWeek> adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        getDefaultInfor();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewJob();
            }
        });
        lvJob.setOnItemClickListener((AdapterView.OnItemClickListener) new MyListViewEvent());
        lvJob.setOnItemLongClickListener(new MyListViewEvent());

    }
    public void mapping(){
        etTitle = (EditText) findViewById(R.id.editcongviec);
        etDesciption = (EditText) findViewById(R.id.editnoidung);
        tvDate = (TextView) findViewById(R.id.txtdate);
        tvTime = (TextView) findViewById(R.id.txttime);
        btnDate = (Button) findViewById(R.id.btndate);
        btnTime = (Button) findViewById(R.id.btntime);
        btnAddJob = (Button) findViewById(R.id.btncongviec);
        lvJob = (ListView) findViewById(R.id.lvcongviec);

        adapter=new ArrayAdapter<JobInWeek> (this,android.R.layout.simple_list_item_1,arrJob);
        lvJob.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDefaultInfor(){
        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        tvDate.setText(strDate);
        dft = new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        tvTime.setText(strTime);
        dft = new SimpleDateFormat("HH:mm",Locale.getDefault());
        tvTime.setTag(dft.format(cal.getTime()));

        etTitle.requestFocus();
        dateFinish = cal.getTime();
        hourFinish = cal.getTime();
    }

    public void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {

                tvDate.setText(
                        (dayOfMonth) + "/" + (monthOfYear+1) + "/" + year);

                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();
            }
        };

        String s = tvDate.getText()+"";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1])-1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(
                MainActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {

                String s = hourOfDay + ":" + minute;
                int hourTam = hourOfDay;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                tvTime.setText
                        (hourTam + ":" + minute + (hourOfDay > 12 ? " PM" : " AM"));

                tvTime.setTag(s);

                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish = cal.getTime();
            }
        };

        String s = tvTime.getTag() + "";
        String strArr[] = s.split(":");
        int gio = Integer.parseInt(strArr[0]);
        int phut = Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                MainActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
    public void addNewJob(){
        String title = etTitle.getText()+"";
        String description = etDesciption.getText()+"";
        JobInWeek job = new JobInWeek(title, description, dateFinish, hourFinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        etTitle.setText("");
        etDesciption.setText("");
    }


    private class MyListViewEvent implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            arrJob.remove(arg2);
            adapter.notifyDataSetChanged();
            return false;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Toast.makeText(MainActivity.this,
                    arrJob.get(arg2).getDescription(),
                    Toast.LENGTH_LONG).show();
        }
    }
}
