package com.example.demo.service;

import com.example.demo.config.VNPayConfig;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Payment;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.PaymentRequest;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    VNPayConfig vnPayConfig;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + paymentId));
    }

    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = modelMapper.map(paymentRequest, Payment.class);

        Set<Booking> bookings = new HashSet<>();
        for(Long idService : paymentRequest.getBookingId()){
            Booking booking = bookingRepository.findById(idService).orElseThrow(() -> new NotFoundException("Service not found"));
            bookings.add(booking);
        }

        payment.setBookings(bookings);


        try{
            Payment newPayment = paymentRepository.save(payment);
            return newPayment;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate stylist found");
        }
    }
    public Payment updatePayment(long paymentId, Payment updatedPayment) {
        Payment newPayment = getPaymentById(paymentId);
        newPayment.setPayment_type(updatedPayment.getPayment_type());
        newPayment.setAmount(updatedPayment.getAmount());
        newPayment.setPayment_date(updatedPayment.getPayment_date());
        newPayment.setBookings(updatedPayment.getBookings());


//        Booking existingBooking = getBookingById(bookingId);
//        existingBooking.setStylist(updatedBooking.getStylist());
//        existingBooking.setCustomer(updatedBooking.getCustomer());
////        existingBooking.setSchedule(updatedBooking.getSchedule());
//        existingBooking.setAppointmentDate(updatedBooking.getAppointmentDate());
//        existingBooking.setStartTime(updatedBooking.getStartTime());
//        existingBooking.setEndTime(updatedBooking.getEndTime());
//        existingBooking.setStatus(updatedBooking.getStatus());
        return paymentRepository.save(newPayment);
    }

//    public void deletePayment(Long bookingId) {
//        Booking existingBooking = getBookingById(bookingId);
//        paymentRepository.delete(existingBooking);
//    }
public String createPaymentUrl(long amount, String orderInfo) throws UnsupportedEncodingException {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String vnp_TxnRef = getRandomNumber(8);
    String vnp_IpAddr = "127.0.0.1";
    String vnp_TmnCode = vnPayConfig.getTmnCode();
    String orderType = "other";
    String locate = "vn";

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
    vnp_Params.put("vnp_CurrCode", "VND");

    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", orderInfo);
    vnp_Params.put("vnp_OrderType", orderType);

    String vnp_ReturnUrl = "http://localhost:5173/success?orderID=";
    vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    vnp_Params.put("vnp_Locale", locate);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
        String fieldName = (String) itr.next();
        String fieldValue = (String) vnp_Params.get(fieldName);
        if ((fieldValue != null) && (fieldValue.length() > 0)) {
            //Build hash data
            hashData.append(fieldName);
            hashData.append('=');
            hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
            //Build query
            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
            query.append('=');
            query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
            if (itr.hasNext()) {
                query.append('&');
                hashData.append('&');
            }
        }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = vnPayConfig.getPaymentUrl() + "?" + queryUrl;
    return paymentUrl;
}

    public String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    private String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}


