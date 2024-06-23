#
# Copyright (C) 2023 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

LOCAL_PATH := $(call my-dir)

ifneq ($(filter timelm,$(TARGET_DEVICE)),)

include $(call all-makefiles-under,$(LOCAL_PATH))

include $(CLEAR_VARS)

# A/B builds require us to create the mount points at compile time.
# Just creating it for all cases since it does not hurt.
FIRMWARE_MOUNT_POINT := $(TARGET_OUT_VENDOR)/firmware_mnt
$(FIRMWARE_MOUNT_POINT): $(LOCAL_INSTALLED_MODULE)
	@echo "Creating $(FIRMWARE_MOUNT_POINT)"
	@mkdir -p $(TARGET_OUT_VENDOR)/firmware_mnt

DSP_MOUNT_POINT := $(TARGET_OUT_VENDOR)/dsp
$(DSP_MOUNT_POINT): $(LOCAL_INSTALLED_MODULE)
	@echo "Creating $(DSP_MOUNT_POINT)"
	@mkdir -p $(TARGET_OUT_VENDOR)/dsp

OP_MOUNT_POINT := $(TARGET_OUT_VENDOR)/OP
$(OP_MOUNT_POINT): $(LOCAL_INSTALLED_MODULE)
	@echo "Creating $(OP_MOUNT_POINT)"
	@mkdir -p $(TARGET_OUT_VENDOR)/OP

ALL_DEFAULT_INSTALLED_MODULES += $(FIRMWARE_MOUNT_POINT) $(DSP_MOUNT_POINT) $(OP_MOUNT_POINT)

CPP_LIBS := libc++_shared.so
CPP32_SYMLINKS := $(addprefix $(TARGET_OUT_VENDOR)/lib/,$(notdir $(CPP_LIBS)))
$(CPP32_SYMLINKS): $(LOCAL_INSTALLED_MODULE)
	@echo "C++ shared lib link: $@"
	@mkdir -p $(dir $@)
	@rm -rf $@
	$(hide) ln -sf /vendor/lib/libc++_shared_snpe.so $@

CPP64_SYMLINKS := $(addprefix $(TARGET_OUT_VENDOR)/lib64/,$(notdir $(CPP_LIBS)))
$(CPP64_SYMLINKS): $(LOCAL_INSTALLED_MODULE)
	@echo "C++ shared lib link: $@"
	@mkdir -p $(dir $@)
	@rm -rf $@
	$(hide) ln -sf /vendor/lib64/libc++_shared_snpe.so $@

ALL_DEFAULT_INSTALLED_MODULES += $(CPP32_SYMLINKS) $(CPP64_SYMLINKS)

QCA6390_FIRMWARE_SYMLINK := $(TARGET_OUT_VENDOR)/firmware/qca6390
$(QCA6390_FIRMWARE_SYMLINK): $(LOCAL_INSTALLED_MODULE)
	@echo "QCA6390 firmware link: $@"
	$(hide) ln -sf /vendor/firmware_mnt/image $(TARGET_OUT_VENDOR)/firmware/qca6390

ALL_DEFAULT_INSTALLED_MODULES += $(QCA6390_FIRMWARE_SYMLINK)

endif
