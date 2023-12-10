#
# Copyright (C) 2023 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

# Enable updating of APEXes
$(call inherit-product, $(SRC_TARGET_DIR)/product/updatable_apex.mk)

# Dalvik Heap Configuration
$(call inherit-product, frameworks/native/build/phone-xhdpi-6144-dalvik-heap.mk)

# Inherit the proprietary files
$(call inherit-product, vendor/lge/sm8150-common/sm8150-common-vendor.mk)
