package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chris Bay
 */
@Controller
@RequestMapping("events")
public class EventController {

    private static Map<String, Map<String, String>> events = new HashMap<>();

    static {
        events.put("Menteaship", Map.of("description", "A fun meetup for connecting with mentors", "image", "https://www.chieflearningofficer.com/wp-content/uploads/2022/04/AdobeStock_498924974-scaled-1.jpeg"));
        events.put("Code With Pride",Map.of("description", "A fun meetup sponsored by LaunchCode", "image", "data:image/jpeg;base64,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"));
        events.put("Javascripty", Map.of("description", "An imaginary meetup for Javascript developers", "image", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAmVBMVEXw208yMzDw2kvz433x3Vjz4nX14FAvMTDz3U8aIC5BQDLSwUr44lAqLC8sLi8nKi9zbDkeIy4YHi4kJy/fzEzq1k6ypETl0U3KuUg5OTGdkUBPTDSJfz01NjF5cTq9rkammUJHRTNfWjYUGy6EezyUiT/FtUdXUzVqZDgNFy25qkWekkCom0JFRDNMSjReWTb16JcBEi1tZjjKeR7KAAAIpUlEQVR4nO2c2XbjNgyG5U5LUhZFmVq9Rd4i2+Nsbd7/4Sp5MoltgRS95RBz+F+0F+NA+sQVIAjvxx+uf72//nD94/3hcoT45QjxyxHilyPEL0eIX44QvxwhfjlC/HKE+OUI8csR4pcjxC9HiF+OEL8cIX45QvxyhPjlCPHLEeKXI8QvR4hfjhC/HCF+OUL8coT45QjxyxHilyPEL0eIX44QvxwhfjlC/HKE+OUI8csR4pcjxC9HiF+OEL8cIX45QvxyhPjlCPHLEeKXI8QvR4hfjhC/HCF+OUL8coT4hYOQEcI5J+zz/2f87XcRckhGL8oIz4K8KpeTxUt/MXl7n85nKasxDZ/8TYSsHLS1TTvfsm60oHqL4iQKhaCNhIgS6Y8GecqJ0aO/iZCMorCln0EHIeNp1fcT0WuJholc7jwTxu8i7NP2a/odhDwtZQL83YeEHM0NGO0lJNmjHyrxfrVk8rDrHMzWEvIdjfR8e0Z/m3U0o6WEzCtjdf88VCSGXPtoOwlZNkmM+PbNmGsRrSRkxahjBB5p9aRDtJGQFX1ggdDIn2sQrSScnNOC+1bcqREtJOSl8Rj8lAyUM6p9hCT3zwbs0YdM9Wj7CDPNNkat6F3VT60j5I8GCz2gOFf0U9sIWSovAqz34op+ahvhpU3Y6yUl3Ii2EWbKeZTWjmHtJqoGqVwM4bXHMkKSKzqpkA/v1dO4KhcJNBMJv1KFNiwj5FtwN1O7EEPGCWnCNOnTqNXO/iZQLvmWEWbgKKTR7MsNZMSr5JG1MJprojZ2EbI1tNrT8KSF+PCgp9btm+LZeZMnaKLxZ6ezJAnEb3tRL9e7+XYR8hLYc4tlu4nIMP7VgKsBLh+fb4CJRq6hrc+8mXST0Vrv4HvWEUI/E+BmhS+FiKfoYm2s1/4ZfQUpWPrzVb1EHMguQg9qwg3MQWaeUWDffkI6UbSU4cGFZYRQLx1d92i7CMkI+JnsPsDRyS5C/gr8LBmbHTIpZBkhtPGm9KpHW0YI+r9RabIqqGQXIXkGHWBZXYFoFyEbwpFEvzRdG9qyi1DhH9azzWJodurflmWE9W4TRhRxmV7GaBmhYiA2iuTjRYyWEbJCE9KPZBkYJmAcyDJCj7/rzp1C/23GzmS0jZB9OO8qCb//lJ3VWW0jVM81v0UT+RicwWgdIQtWesJakb9cE9POah1hvXMzOCAV/iI3HJD2EXqe0Sk+lf1nI0YLCVlgdghM5Sg36KsWEnrc9JybysUaZ9YXH3fPNh+MHSF9z1LCGtE4WyGUY30yrZ2EHp+vjPMV5AbRycyX+DoyzosSUpfZZiuhR1Lz5L1ePFXPqdYSeoxUvnEzyq0yf99ewrqnBhNpOhqTjSrOYTNh3YzPwrSrRhuMhE2ydxUZJtgkAxz5NC3xYqpL2D80NwZnVOsJPcazqmc0Hn0wZ8h+wlo8e+4bzKviBS1h/fd8vZVRV0NKqJ8iIdzfEJqGHQOSQkf+aAgbRm/+ou+s0EEcIsLGDJttdIz0oW0RF2EzIIfbWM3ot3NvsBE2nTVY+qrxGLaPGvERNoyzdgLmRzdtpzVgJKzNeY+KIEDbJk7CehPwDCMmrZR9rISqgFzU8oXREiqyGtqpmngJvUwANmkfDWE3PNyIIZZeyg3u6q+hmwv+fedSZTgITFiLlYTEq/5T3WP6elgKzTXx6eb7poQ8Vd57hc6ufUU7MZ4/JJQWXa2YQUbvScjYk5woPnwGfW6/AM3wYNLsykIggf3E6PcSMj58lT0f7lvwgVkCJXDzovzwHhRxly+jcC89/dmtCOsXW4kmjxBMOyc5sI+EXB3ijeXnDBnPtIhkBs000X1mGkaew18vBucRgvcoaKtL11vq/sGnoJrbvY3RAWT0PushH34Fp+PWBZdaGZDd3AsHJy9Tu0XHhRRooqmXwAKoCe+yp2lKdHz5pFS0Z0g4lyupyIkZeera0kR9DZ1At0/usS/96qC/v2I/O0Fk4P6qJ2fs0MxcQFuUuILPPxkZwKmoN/ctgNMT8ZIePYV5b2Am12GGOktfFEHf5BXKu+TFEvaB22nv1xEed9BPxDD/SrBjPN3Alwr7R99hoQq+CH87PK4K1QTdBJz+RttB4asIWQ7XkKH+JC+aqlycZMOyNbp+KZoeDjGyVicnCL9fDbPa3v4SaW1ySlWB02Oj1xPygepcSMjkdTsYbCdCWSfoJCzGt5qkRBr54eR9Wo2r6aA2qQ5+r9rbxuvaUHd5noowBGeYj38eHb8LK5TXtH/bi6IkikJdSFgAN4iuG4d8en6Rjg+drBXNvkefd2kiubt9zJteUsRi/zIt1+GSmibHEgtgt3ElIdldUIikUfQILOVn16U5Uby+PeHFHz4BvD9WPJxXW+hE0ek28CaEhrmSpwJP+jwSdMw2Wilq1Fy/awsuqCcj4JuvNaJ5JlRL/hA0en0bErBKgFY0UsWZSNq7FDFWpH7dwLfgO8PicZ9aQR7WB2KxuGxgr1T1vm7hH/KZcerSXvGzxndnbHBJnShVC97MA9buNE5eZqUDbKzl8txVIwzVlQduE8Ugxca0+JGI9NGXBjHdKo9AwW/mvxV3z01kpFK4ECeSk7Q7+byJ15h3/Ch81mUJ3yya2ARZOhmj5Mms/i9hRjlCzbQsp5oG9G4ZEWZ8vdGWVKVJ8lgYX3clbLfpTGgTkk67ruzdMqrP+LBMJLwtqT28UVWcdSWL8KB6iROVB0ZD6S/z7lteNz6Z4dluMPKT6OC1aFPVWL5Oh8S4QPWnOcLT+ftIyuTI06w9z9piv8wLk9uINz9dI9wL8umyH8Wx7/v1f3qTwXiWkguv8daQXjobl5tRsjdYW0xGm3JsXtL7HueH+8rpxCvSWhkj5xZRb9trbNRfaG+wyNh5ZdnveULK2HVkoMGzLeKoq3+NHCF+OUL8coT45QjxyxHilyPEL0eIXzXh33+4fvwPWEmcNW2vumIAAAAASUVORK5CYII="));
    }

    @GetMapping
    public String displayAllEvents(Model model) {
        model.addAttribute("title", "All Events");
        model.addAttribute("events", events);
        return "events/index";
    }

    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(
            @RequestParam String eventName,
            @RequestParam String eventDesc,
            @RequestParam String eventImg) {
        events.put(eventName, Map.of("description", eventDesc, "image", eventImg));
        return "redirect:/events";
    }

}
