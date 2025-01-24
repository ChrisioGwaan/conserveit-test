package com.conserveit.controlbg.service;

import com.conserveit.controlbg.entity.SysUser;
import com.conserveit.controlbg.utils.R;

public interface SysUserService {

    SysUser findByUsername(String username);

    R createNew(SysUser sysUser);

}
