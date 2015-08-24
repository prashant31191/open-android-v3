<b> How to enable Logs? </b>

      citrusClient.enableLog(true); // (Make sure you are enabling logs before citrusClient.init() method.)

   1. Logs can be used while debugging any issue.
   2. Logs are disabled by default.
   3. Make sure you are turning off the logs when you are using Production Environment. 
   
<b> Pro-Guard Changes </b>

   If you are using Proguard in your project add the following lines to your configuration:

<pre>
   -keep class com.squareup.okhttp.** { *; }
   -keep interface com.squareup.okhttp.** { *; }
   -dontwarn com.squareup.okhttp.**
   -dontwarn okio.**
   -dontwarn okio.**
   -dontwarn retrofit.**
   -keep class retrofit.** { *; }
   -keepattributes Signature
   -keepattributes Exceptions
   -keep class com.google.gson.** { *; }
   -keep class com.google.inject.** { *; }
   -keep class org.apache.http.** { *; }
   -keep class org.apache.james.mime4j.** { *; }
   -keep class javax.inject.** { *; }
   -keep class com.citrus.** { *; } 
   -keepattributes *Annotation*
</pre>

