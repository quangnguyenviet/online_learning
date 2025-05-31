package com.vitube.online_learning.service;

import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.utils.HMACUtil;

import lombok.RequiredArgsConstructor;

public interface ZaloPayService {


    public Map<String, Object> createOrder(String courseId) throws Exception;
}
