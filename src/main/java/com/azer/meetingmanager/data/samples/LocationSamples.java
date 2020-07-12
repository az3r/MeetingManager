package com.azer.meetingmanager.data.samples;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.azer.meetingmanager.data.models.Location;
import com.azer.meetingmanager.data.models.Meeting;

public class LocationSamples {
    private static Random random = new Random();

    private static List<String> addresses = Arrays.asList(
        "Số 97 Đường 24, Phường Long Thạnh Mỹ, Quận 9, Thành phố Hồ Chí Minh",
        "Số L50/2 Đường Cư xá Phú Lâm A, Phường 12, Quận 6, Thành phố Hồ Chí Minh",
        "72/84 Huỳnh Văn Nghệ, Phường 15, Quận Tân Bình, Thành phố Hồ Chí Minh",
        "Số 54A Dương Quảng Hàm, Phường 5, Quận Gò Vấp, Thành phố Hồ Chí Minh",
        "2Bis, Nguyễn Thị Minh Khai",
        "35-45 Lê Thánh Tôn",
        "172 Nguyễn Duy Trinh, P. Bình Trưng",
        "Siêu thị An Phú, 43 Thảo Điền",    
        "CoopMart, 168 Nguyễn Đình Chiểu",
        "387-389 Hai Bà Trưng",
        "223 Nguyễn Tri Phương"
    );

    private static List<String> locationNames = Arrays.asList(
        "7 Kỳ Quan - Dimsum & Món Hoa",
        "Sông Trăng - Bình Qưới",
        "Nhà Văn hóa Sinh viên ĐHQG",
        "Melisa Center - Trung Tâm Tiệc Cưới Hội Nghị",
        "Buffet Nướng - Khách Sạn Hương Sen",
        "Continental Palace Saigon Buffet",
        "Lotte Legend Saigon Hotel",
        "Gala Royale - Tiệc Cưới & Hội Nghị",
        "The Adora - Tiệc Cưới & Hội Nghị",
        "Callary - Nhà hàng Tiệc Cưới",
        "Vườn Cau - Nhà Hàng Tiệc Cưới"
    );

    public static Location createLocation() {
        int x = random.nextInt(addresses.size());
        int y = random.nextInt(locationNames.size());
        return new Location(locationNames.get(y), addresses.get(x), random.nextInt(201) + 100);
    }

    public static Meeting createMeeting() {
        Location location = createLocation();

        String shortDesc = repeat("short description", 50);
        String detailDesc = repeat("detail description", 100);

        Meeting meeting = new Meeting(shortDesc, detailDesc, photo, holdTime, duration, location)
    }

    private static String repeat(String value, int count) {
        return new String(new char[count]).replace("\0", value);
    }
}