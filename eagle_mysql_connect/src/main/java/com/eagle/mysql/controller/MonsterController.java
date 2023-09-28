package com.eagle.mysql.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eagle.mysql.mapper.MonsterMapper;
import com.eagle.mysql.pojo.dto.query.ListMonsterDTO;
import com.eagle.mysql.pojo.dto.resp.MonsterDTO;
import com.eagle.mysql.pojo.entity.Monster;
import com.eagle.mysql.pojo.enums.GenderEnum;
import com.eagle.mysql.service.IMonsterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-07-02
 */
@RestController
@RequestMapping("/monster")
@Slf4j
public class MonsterController {
    @Resource(name = "monsterServiceImpl")
    private IMonsterService iMonsterService;
    @Resource
    private MonsterMapper monsterMapper;

    @PostMapping("/deleteById")
    public String deleteById(@RequestParam("id") Integer id) {
        log.info("dafsdf");
        iMonsterService.removeById(id);
        return "ok";
    }

    @PostMapping("/addMonster")
    public String addMonster() {
        log.info("dafsdf");
        Monster monster = new Monster();
        monster.setName("hsp");
        monster.setGender(GenderEnum.FEMALE);
        monsterMapper.addMonster2(monster);
        Monster monster1 = monsterMapper.selectById(1);
        log.info("query monster = {}", monster1);
        return monster.toString();
    }

    @PostMapping("/addMonster2")
    public String addMonster2(@RequestParam String name) {
        System.out.println(name);
        log.info("dafsdf");
        Monster monster = new Monster();
        monster.setName("hsp");
        monsterMapper.addMonster2(monster);
        return monster.toString();
    }

    @GetMapping("/query/list")
    public Page<Monster> getList(ListMonsterDTO dto) {
        log.info("query/list dto={}", dto);
        LambdaQueryWrapper<Monster> queryWrapper = new LambdaQueryWrapper<>();
        Page<Monster> page = new Page<>(dto.getCurrentPage(), dto.getPageSize(), true);
        // 没有查到count原因未知
        return monsterMapper.selectPage(page, queryWrapper);
    }

    @GetMapping("/query/monsterList")
    public Page<MonsterDTO> getMonsterList(ListMonsterDTO dto) {
        log.info("getMonsterList dto={}", dto);
        LambdaQueryWrapper<Monster> queryWrapper = new LambdaQueryWrapper<>();
        // Remove the last method, Mybatis Plus will handle pagination
        Page<Monster> monsterPage = new Page<>(dto.getCurrentPage(), dto.getPageSize(), true); //search count
        monsterPage.setSearchCount(true); // 设置searchCount为true，以返回总记录数

         monsterMapper.selectPage(monsterPage, queryWrapper);
        Page<MonsterDTO> dtoPage = new Page<>(dto.getCurrentPage(), dto.getPageSize());
        BeanUtils.copyProperties(monsterPage, dtoPage, "records");
        List<MonsterDTO> dtoList = monsterPage.getRecords().stream().map(MonsterDTO::from).collect(Collectors.toList());
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }
}
