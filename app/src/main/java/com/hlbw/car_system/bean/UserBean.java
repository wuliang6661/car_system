package com.hlbw.car_system.bean;

import java.util.List;

public class UserBean {


    public String id;
    public String createTime;
    public String updateTime;
    public String valid;
    public String version;
    public String deleteTime;
    public String searchValue;
    public String createBy;
    public String updateBy;
    public String remark;
    public ParamsBean params;
    public Integer userId;
    public Integer deptId;
    public String userName;
    public String nickName;
    public String email;
    public String phonenumber;
    public String sex;
    public String avatar;
    public String salt;
    public String status;
    public String delFlag;
    public String loginIp;
    public String loginDate;
    public DeptBean dept;
    public List<RolesBean> roles;
    public String roleIds;
    public String postIds;
    public Integer roleId;
    public String firmName;
    public String legalPerson;
    public String creditCode;
    public String qqNum;
    public String ding;
    public String city;
    public String officeName;
    public String handler;
    public String hCard;
    public String postalCode;
    public String recyclingPhone;
    public String agentName;
    public String agentCard;
    public String agentPhone;
    public String dismantlerName;
    public String scrapName;
    public String licenseImg;
    public String hCardImg;
    public String principalImg;
    public String electronicStamp;
    public String signImg;
    public String users;
    public Boolean admin;

    public static class ParamsBean {
    }

    public static class DeptBean {
        public String id;
        public String createTime;
        public String updateTime;
        public String valid;
        public String version;
        public String deleteTime;
        public String searchValue;
        public String createBy;
        public String updateBy;
        public String remark;
        public ParamsBean params;
        public Integer deptId;
        public Integer parentId;
        public String ancestors;
        public String deptName;
        public String orderNum;
        public String leader;
        public String phone;
        public String email;
        public String status;
        public String delFlag;
        public String parentName;
        public List<?> children;

        public static class ParamsBean {
        }
    }

    public static class RolesBean {
        public String id;
        public String createTime;
        public String updateTime;
        public String valid;
        public String version;
        public String deleteTime;
        public String searchValue;
        public String createBy;
        public String updateBy;
        public String remark;
        public ParamsBean params;
        public Integer roleId;
        public String roleName;
        public String roleKey;
        public String roleSort;
        public String dataScope;
        public Boolean menuCheckStrictly;
        public Boolean deptCheckStrictly;
        public String status;
        public String delFlag;
        public Boolean flag;
        public String menuIds;
        public String deptIds;
        public Boolean admin;

        public static class ParamsBean {
        }
    }
}
