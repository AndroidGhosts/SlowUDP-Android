# SlowUDP-Android
# SlowUDP Android Manager

تطبيق أندرويد لإدارة وتثبيت خوادم SlowUDP (Hysteria) عن بُعد.

## المميزات

- ✅ تثبيت تلقائي لـ SlowUDP على الخوادم البعيدة
- ✅ إدارة الخدمة (بدء/إيقاف/حالة)
- ✅ عرض تكوين الخادم
- ✅ واجهة مستخدم عربية
- ✅ اتصال آمن عبر SSH

## المتطلبات

- خادم Linux مع وصول root
- اتصال SSH مفعل
- Android 5.0+ (API 21)

## طريقة الاستخدام

1. أدخل معلومات الخادم (IP، اسم مستخدم، كلمة مرور)
2. انقر على "تثبيت SlowUDP"
3. انتظر اكتمال التثبيت
4. استخدم الأزرار للتحكم بالخدمة

## البناء من المصدر

```bash
git clone https://github.com/username/SlowUDP-Android.git
cd SlowUDP-Android
./gradlew assembleDebug
