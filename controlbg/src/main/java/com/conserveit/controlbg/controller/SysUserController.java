package com.conserveit.controlbg.controller;

import com.conserveit.controlbg.entity.SysUser;
import com.conserveit.controlbg.service.SysUserService;
import com.conserveit.controlbg.utils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class SysUserController {

    private SysUserService sysUserService;

    @GetMapping("/find")
    public SysUser find(String username) {
        return sysUserService.findByUsername(username);
    }

    @PostMapping("/save")
    public R save(@RequestBody SysUser sysUser) {
        return sysUserService.createNew(sysUser);
    }

}
