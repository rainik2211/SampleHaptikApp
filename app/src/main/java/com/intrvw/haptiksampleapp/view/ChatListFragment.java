package com.intrvw.haptiksampleapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.intrvw.haptiksampleapp.ApplicationController;
import com.intrvw.haptiksampleapp.R;
import com.intrvw.haptiksampleapp.controller.DataHolder;
import com.intrvw.haptiksampleapp.controller.EventController;
import com.intrvw.haptiksampleapp.controller.EventListener;
import com.intrvw.haptiksampleapp.model.User;


/**
 * Created by rainiksoni on 29/01/17.
 */

public class ChatListFragment extends Fragment implements EventListener{

    public static final String TAG = ChatListFragment.class.getSimpleName();

    private RecyclerView chatList;

    LinearLayoutManager layoutManager;

    ChatListAdapter adapter;

    public ChatListFragment(){}

    ImageLoader loader;


    public static ChatListFragment getInstance(){
        ChatListFragment chatListFragment = new ChatListFragment();

        return chatListFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatList = (RecyclerView) view.findViewById(R.id.chat_list);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatList.setLayoutManager(layoutManager);
        adapter = new ChatListAdapter();
        chatList.setAdapter(adapter);

        loader = ApplicationController.getInstance().getVolleyRequester().getImageLoader();

    }

    @Override
    public void onResume() {
        super.onResume();
        EventController.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventController.getInstance().unRegister(this);
    }

    @Override
    public boolean handleEvent(int event, Bundle bundle) {
        boolean wasHandled = false;
        switch (event){
            case EventController.EVENT_STAR_MESSAGE_ADDED:
            case EventController.EVENT_MESSAGES_RECEIVED:
                adapter.notifyDataSetChanged();
                break;


        }

        return wasHandled;
    }


    class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        ChatListAdapter(){}

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder holder = null;
            View v = inflater.inflate(R.layout.item_chat_list, parent, false);
            v.invalidate();
            holder = new ChatListItemsHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            ChatListItemsHolder itemsHolder = (ChatListItemsHolder) holder;
            int size = DataHolder.getInstance().getUserMessageMap().size();

            if (size > 0){
                String userName = DataHolder.getInstance().getUserKeyReference().get(position);
                User user =  DataHolder.getInstance().getUserMessageMap().get(userName);
                String name = user.getName();
                String imgUrl = user.getAvatarUrl();
                int totalNumber = user.getNumberOfMessages();
                int starredMessageNumber = user.getNumberOfStarredMessages();


                itemsHolder.name.setText(name);
                itemsHolder.totalMessagesNumber.setText(totalNumber+"");
                itemsHolder.starredMessageNumber.setText(starredMessageNumber+"");

                if (!imgUrl.equalsIgnoreCase("") && imgUrl != null){
                    loader.get(imgUrl, ImageLoader.getImageListener(itemsHolder.avatar,
                            R.drawable.default_image, R.drawable.default_image));
                    itemsHolder.avatar.setImageUrl(imgUrl, loader);
                }



            }

        }

        @Override
        public int getItemCount() {
            return DataHolder.getInstance().getUserMessageMap().size();
        }
    }


    class ChatListItemsHolder extends RecyclerView.ViewHolder{

        CircleImageView avatar;

        TextView name;

        TextView totalMessagesNumber;

        TextView starredMessageNumber;

        public ChatListItemsHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.user_name);

            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);

            totalMessagesNumber = (TextView) itemView.findViewById(R.id.total_messages_number);

            starredMessageNumber = (TextView) itemView.findViewById(R.id.star_messages_number);
        }

    }
}
