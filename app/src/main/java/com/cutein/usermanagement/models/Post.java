package com.cutein.usermanagement.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String eid;
    public String ename;
    public String date;
    public String salary;


    public Post(String userId, String username, String id, String name, String date, String salary) {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String eid, String ename, String date, String salary) {
        this.uid = uid;
        this.eid = eid;
        this.ename = ename;
        this.date = date;
        this.salary = salary;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("eid", eid);
        result.put("ename", ename);
        result.put("date", date);
        result.put("salary", salary);


        return result;
    }
    // [END post_to_map]

}
// [END post_class]
