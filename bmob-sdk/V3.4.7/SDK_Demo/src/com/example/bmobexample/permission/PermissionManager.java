package com.example.bmobexample.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Ȩ�޹���
 * @author 
 * @class PermissionManager
 * @date 2016-3-25 ����3:54:14
 */
public class PermissionManager {
	
    private Object mObject;
    private String[] mPermissions;
    private int mRequestCode;
    private PermissionListener mListener;
    // �û��Ƿ�ȷ���˽��Ϳ��
    private boolean mIsPositive = false;

    public static PermissionManager with(Activity activity) {
        return new PermissionManager(activity);
    }

    public static PermissionManager with(Fragment fragment) {
        return new PermissionManager(fragment);
    }

    public PermissionManager permissions(String... permissions) {
        this.mPermissions = permissions;
        return this;
    }

    public PermissionManager addRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionManager setPermissionsListener(PermissionListener listener) {
        this.mListener = listener;
        return this;
    }

    public PermissionManager(Object object) {
        this.mObject = object;
    }

    /**����Ȩ��  
     * @return PermissionManager  
     */
    public PermissionManager request() {
        request(mObject, mPermissions, mRequestCode);
        return this;
    }

    private void request(Object object, String[] permissions, int requestCode) {
        // ����Ȩ�޼���ȥ�����Ƿ��Ѿ���Ȩ��
        Map<String, List<String>> map = findDeniedPermissions(getActivity(object), permissions);
        List<String> deniedPermissions = map.get("deny");
        List<String> rationales = map.get("rationale");
        if (deniedPermissions.size() > 0) {
            // ��һ�ε��deny�ŵ��ã�mIsPositive��Ϊ�˷�ֹ��ȷ�Ͻ��Ϳ���request()�ݹ��onShowRationale
            if (rationales.size() > 0 && mIsPositive == false) {
                if (mListener != null ) {
                    mListener.onShowRationale(rationales.toArray(new String[rationales.size()]));
                }
                return;
            }
            if (object instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) object, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if (object instanceof Fragment) {
                ((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }
        } else {
            if (mListener != null) {
                mListener.onGranted();
            }
        }
    }

    /**����requestCode������Ӧ��Ȩ��
     * @param permissions
     * @param results   
     */
    public void onPermissionResult(String[] permissions, int[] results) {
        List<String> deniedPermissions = new ArrayList<String>();
        for (int i = 0; i < results.length; i++) {
            if (results[i] != PackageManager.PERMISSION_GRANTED) {//δ��Ȩ
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0) {
            if (mListener != null) {
                mListener.onDenied();
            }
        } else {
            if (mListener != null) {
                mListener.onGranted();
            }
        }
    }
    
    private Map<String, List<String>> findDeniedPermissions(Activity activity, String... permissions) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> denyList = new ArrayList<String>();//δ��Ȩ��Ȩ��
        List<String> rationaleList = new ArrayList<String>();//��Ҫ��ʾ��ʾ���Ȩ��
        for (String value : permissions) {
            if (ContextCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                denyList.add(value);
                if (shouldShowRequestPermissionRationale(value)) {
                    rationaleList.add(value);
                }
            }
        }
        map.put("deny", denyList);
        map.put("rationale", rationaleList);
        return map;
    }

    private Activity getActivity(Object object) {
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof Activity) {
            return (Activity) object;
        }
        return null;
    }

    /**
     * ���û��ܾ�ĳȨ��ʱ������Ͳ������ѵİ�ťʱ���´�Ӧ���������Ȩ��ʱ����Ҫ�������ʵ���Ӧ���������չʾ�Ի���
     * @param permission
     */
    private boolean shouldShowRequestPermissionRationale(String permission) {
        if (mObject instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) mObject, permission);
        } else if (mObject instanceof Fragment) {
            return ((Fragment) mObject).shouldShowRequestPermissionRationale(permission);
        } else {
            throw new IllegalArgumentException(mObject.getClass().getName() + " is not supported");
        }
    }

    public void setIsPositive(boolean isPositive) {
        this.mIsPositive = isPositive;
    }
}
