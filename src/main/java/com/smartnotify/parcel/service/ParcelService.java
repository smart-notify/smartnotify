package com.smartnotify.parcel.service;

import com.smartnotify.parcel.model.DeliveryStatus;
import com.smartnotify.parcel.model.Parcel;

import java.util.List;

public interface ParcelService {

    List<Parcel> getParcelsByStatus(DeliveryStatus status);

    void sendParcelNotification(String labelContent);

    void deliverParcel(String id, String deliveryCode);

}
