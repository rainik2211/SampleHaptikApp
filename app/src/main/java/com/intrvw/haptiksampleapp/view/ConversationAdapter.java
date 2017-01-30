package com.intrvw.haptiksampleapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.intrvw.haptiksampleapp.ApplicationController;
import com.intrvw.haptiksampleapp.R;
import com.intrvw.haptiksampleapp.controller.DataHolder;
import com.intrvw.haptiksampleapp.controller.EventListener;
import com.intrvw.haptiksampleapp.controller.Utils;
import com.intrvw.haptiksampleapp.model.Message;

/**
 * Created by rainiksoni on 28/01/17.
 */

public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    View.OnClickListener listener;
    Context context;
    ImageLoader loader;

    public static final int ITEM_TYPE_OUTGOING_MESSAGES = 0;

    public static final int ITEM_TYPE_INCOMING_MESSAGES = 1;

    public static final int ITEM_TYPE_EMPTY_CONTAINER = 2;

    public ConversationAdapter(Context context, View.OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        loader = ApplicationController.getInstance().getVolleyRequester().getImageLoader();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;

        switch (viewType){

            case ITEM_TYPE_EMPTY_CONTAINER: {
                View v = inflater.inflate(R.layout.item_empty_container, parent, false);
                v.invalidate();
                holder = new EmptyContainerHolder(v);
            }
                break;

            case ITEM_TYPE_INCOMING_MESSAGES: {
                View v = inflater.inflate(R.layout.item_incoming_chat, parent, false);
                v.invalidate();
                holder = new IncomingMessageHolder(v);
            }
                break;

            case ITEM_TYPE_OUTGOING_MESSAGES: {
                View v = inflater.inflate(R.layout.item_outgoing_message, parent, false);
                v.invalidate();
                holder = new OutgoingMessagHolder(v);
            }
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)){

            case ITEM_TYPE_EMPTY_CONTAINER:
                //this is defined to add one extra empty container to see the complete list
                // so that list is not hidden by edittext box on Conversation page
                EmptyContainerHolder containerHolder  = (EmptyContainerHolder) holder;
                break;

            case ITEM_TYPE_INCOMING_MESSAGES:

                IncomingMessageHolder messageHolder = (IncomingMessageHolder) holder;
                int size = DataHolder.getInstance().getMessageDataMap().size();
                if (size > 0) {
                    Message message = DataHolder.getInstance().getMessageDataMap().get(String.valueOf(position + 1));

                    String username = message.getName();
                    String messageBody = message.getBody();
                    String time = message.getMessageTime();
                    String avatarUrl = message.getImageUrl();
                    boolean isStarred = message.isStarred();
                    messageHolder.itemView.setTag(position);
                    messageHolder.userName.setText(username);
                    messageHolder.messageBody.setText(messageBody);
                    messageHolder.timeText.setText(Utils.getTime(time));

                    if (!avatarUrl.equalsIgnoreCase("") || avatarUrl != null){
                        loader.get(avatarUrl, ImageLoader.getImageListener(messageHolder.avatar,
                                R.drawable.default_image, R.drawable.default_image));
                        messageHolder.avatar.setImageUrl(avatarUrl, loader);
                    }

                    if (isStarred){
                        messageHolder.starImage.setVisibility(View.VISIBLE);
                    }else {
                        messageHolder.starImage.setVisibility(View.GONE);
                    }

                }
                break;

            case ITEM_TYPE_OUTGOING_MESSAGES:

                OutgoingMessagHolder outgoingMessagHolder = (OutgoingMessagHolder) holder;

                Message message = DataHolder.getInstance().getMessageDataMap().get(String.valueOf(position+1));
                outgoingMessagHolder.sentMessage.setText(message.getBody());
                outgoingMessagHolder.time.setText(message.getMessageTime());
                break;
        }


    }

    @Override
    public int getItemCount() {
        return DataHolder.getInstance().getMessageDataMap().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (DataHolder.getInstance().getMessageDataMap().size() > 0){

            Message message = DataHolder.getInstance().
                    getMessageDataMap().get(String.valueOf(position+1));
            boolean isIncoming = false;

            if (message != null){
                isIncoming = message.isIncoming();
                if (!isIncoming){
                    type = ITEM_TYPE_OUTGOING_MESSAGES;
                }else {
                    type = ITEM_TYPE_INCOMING_MESSAGES;
                }
            }else {
                type = ITEM_TYPE_EMPTY_CONTAINER;
            }
        } else {
            type = ITEM_TYPE_EMPTY_CONTAINER;
        }

        return type;
    }

    class IncomingMessageHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView messageBody;
        TextView timeText;
        ImageView starImage;
        CircleImageView avatar;

        public IncomingMessageHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(listener);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            messageBody = (TextView) itemView.findViewById(R.id.message_tv);
            timeText = (TextView) itemView.findViewById(R.id.time_tv);
            starImage = (ImageView) itemView.findViewById(R.id.star_iv);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar_iv);


        }

    }

    class OutgoingMessagHolder extends RecyclerView.ViewHolder {


        TextView sentMessage;
        TextView time;

        public OutgoingMessagHolder(View itemView) {
            super(itemView);
            sentMessage = (TextView) itemView.findViewById(R.id.sent_message);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    class EmptyContainerHolder extends RecyclerView.ViewHolder {

        public EmptyContainerHolder(View itemView) {
            super(itemView);
        }

    }


}
