package com.intrvw.haptiksampleapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.intrvw.haptiksampleapp.R;
import com.intrvw.haptiksampleapp.controller.DataController;
import com.intrvw.haptiksampleapp.controller.DataHolder;
import com.intrvw.haptiksampleapp.controller.EventController;
import com.intrvw.haptiksampleapp.controller.EventListener;
import com.intrvw.haptiksampleapp.model.Message;
import com.intrvw.haptiksampleapp.model.User;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by rainiksoni on 27/01/17.
 */

public class ConversationFragment extends Fragment implements View.OnClickListener, EventListener{

    public static final String TAG = ConversationFragment.class.getSimpleName();

    private RecyclerView conversationList;

    private View view;

    public ConversationFragment (){}

    public ConversationAdapter adapter;

    LinearLayoutManager linearLayoutManager;

    LinearLayout progressBarLayout;

    EditText messageToSend;

    ImageView sendChat;

    public static final ConversationFragment getInstance(){
        ConversationFragment conversationFragment = new ConversationFragment();

        return conversationFragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_conversation, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        conversationList = (RecyclerView) view.findViewById(R.id.conversation_message_list);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        conversationList.setLayoutManager(linearLayoutManager);

        adapter = new ConversationAdapter(getActivity(), this);
        conversationList.setAdapter(adapter);
        progressBarLayout = (LinearLayout) view.findViewById(R.id.progress_bar);
        messageToSend = (EditText) view.findViewById(R.id.chat_edit_text);
        sendChat = (ImageView) view.findViewById(R.id.enter_chat1);
        sendChat.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        showErrorDialog("Click on messages to make them important", 0);
        EventController.getInstance().register(this);
        if (DataHolder.getInstance().getUserMessageMap().size() > 0){
            progressBarLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventController.getInstance().unRegister(this);
    }




    @Override
    public boolean handleEvent(int event, Bundle bundle) {
        switch (event){
            case EventController.EVENT_STAR_MESSAGE_ADDED:
            case EventController.EVENT_MESSAGES_RECEIVED:
                adapter.notifyDataSetChanged();
                progressBarLayout.setVisibility(View.GONE);

                break;

            case EventController.EVENT_BAD_REQUEST:
                showErrorDialog("Bad request try again", EventController.EVENT_BAD_REQUEST);
                break;

            case EventController.EVENT_REQUEST_TIMEOUT:
                showErrorDialog("Connection error try again", EventController.EVENT_REQUEST_TIMEOUT);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        int key  = 0;


        if (view.getId() == R.id.enter_chat1){
            sendMessage();
        } else if (view.getTag() != null && view.getTag() instanceof Integer ){

            key = ((Integer) view.getTag()).intValue();

            Message message = DataHolder.getInstance().getMessageDataMap().get(String.valueOf(key+1));
            String userKey = message.getUserName();
            User user = DataHolder.getInstance().getUserMessageMap().get(userKey);

            if (!message.isStarred()){
                user.setNumberOfStarredMessages(user.getNumberOfStarredMessages()+1);
                message.setStarred(true);

            }else {
                user.setNumberOfStarredMessages(user.getNumberOfStarredMessages()-1);
                message.setStarred(false);
            }
            EventController.getInstance().notify(EventController.EVENT_STAR_MESSAGE_ADDED, null);
            conversationList.getAdapter().notifyItemChanged(key);

        }

    }



    public void showErrorDialog(String message, final int code){


        final Snackbar snackBar = Snackbar.make(view,
                message, Snackbar.LENGTH_LONG);

        snackBar.setAction(getString(R.string.ok_string), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (code == EventController.EVENT_BAD_REQUEST || code == EventController.EVENT_REQUEST_TIMEOUT){
                    DataController.getInstance().initRequestData();
                }
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }


    public void sendMessage(){

        int messageListSize = DataHolder.getInstance().getMessageDataMap().size();
        String messageTyped = messageToSend.getText().toString().trim();
        messageToSend.setText("");

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        String time  = hour+":"+min;

        if (!messageTyped.equalsIgnoreCase("")){
            Message message = new Message(messageTyped, "self_username", "You", "none", time, false, false);
            DataHolder.getInstance().getMessageDataMap().put(String.valueOf(messageListSize+1), message);
            //hide the keyboard

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            conversationList.smoothScrollToPosition(DataHolder.getInstance().getMessageDataMap().size()-1);

            adapter.notifyDataSetChanged();
        }else {
            showErrorDialog("Add message to send", 0);
        }

    }
}
