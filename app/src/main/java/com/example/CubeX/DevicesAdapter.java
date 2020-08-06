package com.example.CubeX;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceAdapterVh> {



    private List<Device> deviceList;
    private Context context;
    private SelectedDevice  selectedDevice;


    public DevicesAdapter(List<Device> deviceList,SelectedDevice  selectedDevice) {
        this.deviceList = deviceList;
        this.selectedDevice = selectedDevice;
    }

    @NonNull
    @Override
    public DevicesAdapter.DeviceAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new DeviceAdapterVh(LayoutInflater.from(context).inflate(R.layout.device_layout, null));
    }

    @Override
    public void onBindViewHolder(@NonNull DevicesAdapter.DeviceAdapterVh holder, int position) {

        Device device = deviceList.get(position);

        String nickname = device.getNickname();
        int battery = device.getBattery();
        String fillStatus = device.getFillStatus();
        int deviceImage = device.getDeviceImage();
        int batteryIcon = device.getBatteryIcon();
        int fillIcon = device.getFillIcon();
        //String identifier = device.getIdentifier();



        holder.nickname.setText(nickname);
        holder.fillStatus.setText(fillStatus);
        //holder.batteryLevel.setText(battery);
        holder.deviceImage.setImageResource(deviceImage);
        holder.batteryIcon.setImageResource(batteryIcon);
        holder.fillRateIcon.setImageResource(fillIcon);

    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public interface SelectedDevice
    {
        void selectedDevice(Device device);

    }

    public class DeviceAdapterVh extends RecyclerView.ViewHolder {

        ImageView deviceImage;
        ImageView batteryIcon;
        ImageView fillRateIcon;
        TextView nickname;
        TextView batteryLevel;
        TextView fillStatus;



        public DeviceAdapterVh(@NonNull View itemView) {
            super(itemView);


            deviceImage = itemView.findViewById(R.id.Deviceimage);
            batteryIcon = itemView.findViewById(R.id.batteryIcon);
            fillRateIcon = itemView.findViewById(R.id.fillRateIcon);
            nickname = itemView.findViewById(R.id.nickname);
            batteryLevel = itemView.findViewById(R.id.batteryLevel);
            fillStatus = itemView.findViewById(R.id.fillRateText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedDevice.selectedDevice(deviceList.get(getAdapterPosition()));
                }
            });

        }
    }
}


