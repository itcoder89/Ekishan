package com.android.ekishan.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ekishan.ApiClient.ApiClient;
import com.android.ekishan.ApiClient.ApiInterface;
import com.android.ekishan.MyApplication;
import com.android.ekishan.R;
import com.android.ekishan.chat.model.APIChatModelData;
import com.android.ekishan.chat.model.APISendMessageData;
import com.android.ekishan.chat.model.ChatModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by sanjay on 15/5/17.
 */

public class CustomerSingleChatActivity extends AppCompatActivity {
    ListView lv_Chat;
    public ChatAdapter adapter;
    ImageView iv_backbtn;
    String receiver_name;
    String receiverId;
    TextView tv_chatHeaderName;
    String senderId;
    ImageView btnSend;
    private String mMineMessage;
    public ArrayList<ChatModel> list;
    EditText edChatMessage;
    String myid;
    private ProgressDialog prgDialog;
    ApiInterface apiService;
    Timer timer;
    private static AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_chat_window);
        getSupportActionBar().hide();
        lv_Chat=(ListView)findViewById(R.id.lv_Chat);
        iv_backbtn=(ImageView)findViewById(R.id.iv_back);
        tv_chatHeaderName=(TextView)findViewById(R.id.tvHeaderName);
        edChatMessage=(EditText)findViewById(R.id.edChatMessage);
        btnSend=(ImageView)findViewById(R.id.btnSend);
        prgDialog = new ProgressDialog(this);
        list=new ArrayList<ChatModel>();
        arrMessageList=new ArrayList<APIChatModelData.DataBean>();
        loadSavedPreferences();
        Bundle extras = getIntent().getExtras();
        Log.d("Bundle", "ChatScreen");
        if (extras != null) {
            //get chat user name value
            receiverId = extras.getString("receiverId");// Chat Receiver ID
            receiver_name = extras.getString("receiverName");
            //Log.e("receiverID"," "+receiverId);
            //Log.e("receiver_name"," "+receiver_name);
        }
        tv_chatHeaderName.setText("ADMIN");
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //getMessage();
        getAPIMessageList();

        iv_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerSingleChatActivity.this.finish();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //Log.e("premium","CommanApiCall"+CommanApi.arrayList.get(0).getPremium());

                        // check customer plan
                        mMineMessage = edChatMessage.getText().toString();
                        edChatMessage.setText("");
                        scrollMyListViewToBottom();
                        if (mMineMessage.length() == 0) {
                            Toast.makeText(getApplicationContext(),"Please Enter Text", Toast.LENGTH_SHORT).show();
                        } else {
                            ChatModel model=new ChatModel();
                            //model.setUsername(senderId+"");// name without domain name
                            model.setText(mMineMessage);
                            model.setSide(ChatModel.SIDE_RIGHT);
                            list.add(model);
                            //sendMessage();
                            sendAPIChatMessage(mMineMessage);
                            setListAdapter();
                            closeKeyBoard();
                        }



                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        lv_Chat.setAdapter(adapter);
        try {
            callAsyncTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage()
    {

        RequestParams params=new RequestParams();
        JSONObject obj=new JSONObject();
        try
        {
            obj.put("sender_id","1");
            obj.put("receiver_id","3");
            obj.put("message",mMineMessage);
            params.put("request", obj.toString());
            Log.e("param"," "+params.toString());
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //  Log.d("GetInvitationrequest", params.toString());
        client.post("http://chaurasiamilanapp.com/api/sendChatMessage", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        Log.d("onStart","start service");
                    }

                    @Override
                    public void onFinish() {
                        Log.d("onFinish","Finish service");
                    }

                    @Override
                    public void onFailure(Throwable e, JSONObject errorResponse) {
                        super.onFailure(e, errorResponse);
                        Log.d("onFailure","Failer service"+errorResponse);
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        super.onSuccess(response);
                        Log.e("sendMessageRes","--->"+response);
                        if(response.length()>0){
                            try {
                                boolean b=response.optBoolean("result");
                                String msg=response.optString("message");
                                if(b){
                                    //Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
                                }else {
                                    //Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    public void callAsyncTask() {

        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //put your code here
                           //getMessage();
                            getAPIMessageList();
                        } catch (Exception e) {


                        }

                    }
                });
            }
        };

        timer.schedule(doAsyncTask, 0, 10000);//refresh every 10 seconds

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListAdapter() {

        adapter=new ChatAdapter(CustomerSingleChatActivity.this, list);
        lv_Chat.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    List<APIChatModelData.DataBean> arrMessageList;
    private void getAPIMessageList() {

        Call<APIChatModelData> responseCall = apiService.getMessage(MyApplication.getCustomerId(),"customer");//login vendor id
       /// Call<APIChatModelData> responseCall = apiService.getMessage("1");//login vendor id
        responseCall.enqueue(new Callback<APIChatModelData>() {
            @Override
            public void onResponse(Call<APIChatModelData> call, Response<APIChatModelData> response) {
                ChatModel model=null;
                list.clear();
                arrMessageList.clear();
                arrMessageList=response.body().getData();

                for(int i=0;i<arrMessageList.size();i++){
                    model = new ChatModel();
                    model.setText(arrMessageList.get(i).getMessage());
                    myid=arrMessageList.get(i).getSender()+"";
                    if(myid.equals(MyApplication.getCustomerId())){
                    //if(myid.equals("1")){
                        // Log.i("Messaging Side Getting","Right");
                        model.setSide(ChatModel.SIDE_RIGHT);
                        // model.setDatetime(list.get(i).getDatetime());
                    }else{
                        //Log.i("Messaging Side Getting","Left");
                        model.setSide(ChatModel.SIDE_LEFT);
                        //model.setDatetime(list.get(i).getDatetime());
                    }
                    list.add(model);
                }

                adapter=new ChatAdapter(CustomerSingleChatActivity.this, list);
                lv_Chat.setAdapter(adapter);
                scrollMyListViewToBottom();

            }

            @Override
            public void onFailure(Call<APIChatModelData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
               // Toast.makeText(CustomerSingleChatActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendAPIChatMessage(String msg) {

        Call<APISendMessageData> responseCall = apiService.sendMessage(MyApplication.getCustomerId(),msg,"customer");//login vendor id
       // Call<APISendMessageData> responseCall = apiService.sendMessage("1",msg);//login vendor id
        responseCall.enqueue(new Callback<APISendMessageData>() {
            @Override
            public void onResponse(Call<APISendMessageData> call, Response<APISendMessageData> response) {
                Log.e("onResponse", "msg:" +response.isSuccessful());
            }

            @Override
            public void onFailure(Call<APISendMessageData> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getLocalizedMessage());
                //Toast.makeText(CustomerSingleChatActivity.this, "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getMessage()
    {

        RequestParams params=new RequestParams();
        JSONObject obj=new JSONObject();
        try
        {
            obj.put("sender_id","3");
            obj.put("receiver_id","1");
            params.put("request", obj.toString());
            Log.e("param"," "+params.toString());
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //  Log.d("GetInvitationrequest", params.toString());
        client.post("http://chaurasiamilanapp.com/api/getChatMessage", params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        Log.d("onStart","start service");
                    }

                    @Override
                    public void onFinish() {
                        Log.d("onFinish","Finish service");
                    }

                    @Override
                    public void onFailure(Throwable e, JSONObject errorResponse) {
                        super.onFailure(e, errorResponse);
                        Log.d("onFailure","Failer service"+errorResponse);
                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        super.onSuccess(response);
                        Log.e("sendMessageRes","--->"+response);
                        if(response.length()>0){
                            //Receive Response TO GSON
                            try {
                                String res=response.getString("Result");
                                String msg=response.getString("message");
                                ChatModel model=null;
                                if(res.equals("true")){
                                    // Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                    JSONArray jarr=response.getJSONArray("data");
                                    list.clear();
                                    for(int i=0;i<jarr.length();i++){
                                        JSONObject obj=jarr.optJSONObject(i);

                                        model = new ChatModel();
                                        model.setText(obj.getString("message"));
                                        //Log.e("senderid","id "+senderId);
                                       // String strSenderid=senderId+"";// convert int sender id to string
                                        String strSenderid="1";// convert int sender id to string
                                        Log.e("mid",""+obj.getString("sender_id"));
                                        myid=obj.getString("sender_id");

                                        if(myid.equals(strSenderid)){
                                            // Log.i("Messaging Side Getting","Right");
                                            model.setSide(ChatModel.SIDE_RIGHT);
                                            // model.setDatetime(list.get(i).getDatetime());
                                        }else{
                                            //Log.i("Messaging Side Getting","Left");
                                            model.setSide(ChatModel.SIDE_LEFT);
                                            //model.setDatetime(list.get(i).getDatetime());
                                        }
                                        list.add(model);
                                    }

                                    adapter=new ChatAdapter(CustomerSingleChatActivity.this, list);
                                    lv_Chat.setAdapter(adapter);
                                    scrollMyListViewToBottom();

                                }else {
                                    Toast.makeText(getApplicationContext(),"No Conversations Found",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
    }

    private void scrollMyListViewToBottom() {

        lv_Chat.post(new Runnable() {
            @Override
            public void run() {

                lv_Chat.setSelection(adapter.getCount() - 1);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        //mHandler.postDelayed(null);

    }
    private void closeKeyBoard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    /*called for getting message list
    * from server
    *
    * */
 /*   private void getMessage() {
       // dialog.setMessage("please wait a moment..");
       // dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.getmessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String WebResponse) {
                        Log.e("getMessageRes", WebResponse);
                       // dialog.dismiss();
                        if(WebResponse.length()>0){
                            //Receive Response TO GSON
                            try {
                                JSONObject jobj=new JSONObject(WebResponse);
                                String res=jobj.getString("result");
                                String msg=jobj.getString("message");
                                ChatModel model=null;
                                if(res.equals("true")){
                                    // Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                                    JSONArray jarr=jobj.getJSONArray("data");
                                    list.clear();
                                    for(int i=0;i<jarr.length();i++){
                                        JSONObject obj=jarr.optJSONObject(i);

                                        model = new ChatModel();
                                        model.setText(obj.getString("message"));
                                        //Log.e("senderid","id "+senderId);
                                        String strSenderid=senderId+"";// convert int sender id to string
                                        myid=obj.getString("senderid");
                                        if(myid.equals(strSenderid)){
                                           // Log.i("Messaging Side Getting","Right");
                                            model.setSide(ChatModel.SIDE_RIGHT);
                                            // model.setDatetime(list.get(i).getDatetime());
                                        }else{
                                            //Log.i("Messaging Side Getting","Left");
                                            model.setSide(ChatModel.SIDE_LEFT);
                                            //model.setDatetime(list.get(i).getDatetime());
                                        }
                                        list.add(model);
                                    }

                                    adapter=new ChatAdapter(SingleChatActivity.this, list);
                                    lv_Chat.setAdapter(adapter);
                                    scrollMyListViewToBottom();

                                }else {
                                    Toast.makeText(getApplicationContext(),"No Conversations Found",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //dialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("ERROR", error.toString());

                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("senderid",senderId+"");
                params.put("receiverid",receiverId+"");
                Log.e("param"," "+params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }*/


    public class ChatAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<ChatModel> list;
        //private ImageLoader imageLoader;

        public ChatAdapter(Context context, ArrayList<ChatModel> list) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.list = list;
            //imageLoader = new ImageLoader(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return list.indexOf(list.get(position));
        }

        /* private view holder class */
        private class ViewHolder {
            TextView txtChatLeft;
            TextView txtChatRight;
            //TextView ldts;
           // TextView rdts;
            //ImageView cirImvLeft;
            RelativeLayout left_ly;
            RelativeLayout right_ly;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {

                convertView = mInflater.inflate(R.layout.single_chat_list_item,parent, false);


                holder = new ViewHolder();
                holder.txtChatLeft = (TextView) convertView.findViewById(R.id.lefttext);
                holder.txtChatRight = (TextView) convertView.findViewById(R.id.righttext);
               // holder.ldts=(TextView)convertView.findViewById(R.id.left_datetime_tv);
               // holder.rdts=(TextView)convertView.findViewById(R.id.right_datetime_tv);
                holder.left_ly = (RelativeLayout) convertView.findViewById(R.id.left_ly);
                holder.right_ly = (RelativeLayout) convertView.findViewById(R.id.right_ly);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ChatModel chatItem = (ChatModel)getItem(position);

            if (chatItem.getSide().equals(ChatModel.SIDE_LEFT)) {

               // Log.e("Adapter Msg Side", "left Side"+chatItem.getSide());
               // Log.e("Adapter Set DateTime:", "left "+chatItem.getDatetime());
                holder.txtChatLeft.setText(chatItem.getText());
                holder.txtChatLeft.setGravity(Gravity.LEFT);
               // holder.ldts.setText(chatItem.getDatetime());
                holder.right_ly.setVisibility(View.INVISIBLE);
                holder.left_ly.setVisibility(View.VISIBLE);
            } else if (chatItem.getSide().equals(ChatModel.SIDE_RIGHT)) {
                //Log.e("Adapter Msg Side", "right Side");
               // Log.e("Adapter Set DateTime:", "right "+chatItem.getDatetime());
                holder.txtChatRight.setText(chatItem.getText());
                holder.txtChatRight.setGravity(Gravity.RIGHT);
                //holder.rdts.setText(chatItem.getDatetime());
                holder.right_ly.setVisibility(View.VISIBLE);
                holder.left_ly.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

    }
    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        senderId = sharedPreferences.getString("CustomerId", null);
        //prem_plan = sharedPreferences.getString("prem_plan", null);
        Log.d("loadSavedLoginPref", "senderId " + senderId);
       // Log.d("loadSavedLoginPref", "prem_plan " + prem_plan);

    }
}
