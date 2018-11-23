# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
## �����Էǹ����Ŀ���
-dontskipnonpubliclibraryclasses
 # �Ƿ�ʹ�ô�Сд���
-dontusemixedcaseclassnames
  # ����ʱ�Ƿ���ԤУ��
-dontpreverify
-verbose
-dontoptimize
#
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # ����ʱ�����õ��㷨
## ����ע��
-keepattributes *Annotation*
##������Щ�಻������
#
## ������Щ�಻������
-keep public class * extends android.support.** #���������v4����v7���������
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.isgala.xishuashua.view.*
##���������v4�����������������
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v7.app.Fragment
###��¼���ɵ���־����,gradle buildʱ�ڱ���Ŀ��Ŀ¼��� start##
##apk �������� class ���ڲ��ṹ
-dump proguard/class_files.txt
##δ��������ͳ�Ա
-printseeds proguard/seeds.txt
##�г��� apk ��ɾ���Ĵ���
-printusage proguard/unused.txt
##����ǰ���ӳ��
-printmapping proguard/mapping.txt
#########��¼���ɵ���־���ݣ�gradle buildʱ �ڱ���Ŀ��Ŀ¼���-end######
   #����������jar��������xxxΪjar����
 # ���� native ������������
-keepclasseswithmembernames class * {
    native <methods>;
}
# �����Զ���ؼ��಻������
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
  # �����Զ���ؼ��಻������
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}



#���� Serializable ��������
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#����ö�� enum �಻������
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}


    -keep class com.zhuochi.hydream.bean_.** { *; }
     -keep class  com.zhuochi.hydream.entity.** { *; }
       -keep class  com.klcxkj.** { *; }


# ButterKnife
    -keep class butterknife.** { *; }
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


    -dontwarn butterknife.internal.**
    -keep class **$$ViewBinder { *; }

    -keepclasseswithmembernames class * {
        @butterknife.* <fields>;
    }

    -keepclasseswithmembernames class * {
        @butterknife.* <methods>;
    }

#gson
#������õ�Gson�������ģ�ֱ����������⼸�о��ܳɹ���������Ȼ�ᱨ��
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }


# ���ʹ����Gson֮��Ĺ���Ҫʹ����������JavaBean�༴ʵ���಻��������
-keep class com.matrix.app.entity.json.** { *; }
-keep class com.matrix.appsdk.network.model.** { *; }


#֧������������

#-libraryjars libs/alipaySdk-20161009.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

#΢��֧��
#-libraryjars libs/libammsdk.jar
-keep class com.tencent.** { *;}

# ������������
-keep class com.nineoldandroids.** { *; }
-keep interface com.nineoldandroids.** { *; }
-dontwarn com.nineoldandroids.**


-keep class com.qiniu.android.** { *; }
-keep interface com.qiniu.android.** { *; }
-dontwarn com.qiniu.android.**


#������ָ���ļ�
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

  # ���Ծ���
-ignorewarning
