#!/bin/bash
# إصدار مختصر من السكريبت الأصلي

echo "بدء تثبيت SlowUDP..."

# الكود الأساسي للتثبيت
wget -N https://raw.githubusercontent.com/evozi/hysteria-install/main/slowudp/install_server.sh
bash install_server.sh

echo "تم الانتهاء من التثبيت"
