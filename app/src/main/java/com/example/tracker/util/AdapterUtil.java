package com.example.tracker.util;

import com.example.tracker.R;
import com.example.tracker.dto.ChannelDto;

import java.util.ArrayList;
import java.util.List;

public class AdapterUtil {
    public static List<ChannelDto> getChannelList(){
        List<ChannelDto> channels = new ArrayList<>();
        channels.add(new ChannelDto(R.drawable.phonepe_icon,"Phone Pay"));
        channels.add(new ChannelDto(R.drawable.google_pay_icon,"Google Pay"));
        channels.add(new ChannelDto(R.drawable.amazon_pay_icon,"Amazon Pay"));
        channels.add(new ChannelDto(R.drawable.paytm_icon,"Paytm"));
        channels.add(new ChannelDto(R.drawable.upi_icon,"UPI"));
        channels.add(new ChannelDto(R.drawable.net_banking_icon,"Net-Banking"));
        channels.add(new ChannelDto(R.drawable.cash_icon,"Cash"));
        return channels;
    }
}
