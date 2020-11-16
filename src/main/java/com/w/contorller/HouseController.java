package com.w.contorller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w.dto.QueryDto;
import com.w.entity.*;
import com.w.service.*;
import com.w.utils.DateUtils;
import com.w.utils.Result;
import com.w.utils.ResultReturn;
import com.w.utils.StringToInt;
import com.w.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:wu
 * @Date :create in 15:22 2020/4/6
 */
@Slf4j
@Api(value = "HouseController", tags = "房源模块")
@RestController
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private UserService userService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private BusinessService businessService;


    @ApiOperation("添加房源信息")
    @PostMapping("/api/house/insert")
    public Result insert(@RequestBody InsertHouseVo houseVo) throws Exception {
        Result result = new Result();
        House house = new House();
        StringToInt stringToInt = new StringToInt();
        //  NumUtils numUtils = new NumUtils();
        DateUtils dateUtils = new DateUtils();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        house.setCreateTime(df.parse(df.format(new Date())));
        house.setIsRecommend(houseVo.getIsRecommend());
        house.setMaxFloorarea(houseVo.getMaxfloorArea());
        house.setMinFloorarea(houseVo.getMinfloorArea());
        house.setActualFloor(houseVo.getActualFloor());
        house.setAddress(houseVo.getAddress());
        house.setApart(houseVo.getApart());
        if (houseVo.getType().equals(1)) {
            if (!StringUtils.isEmpty(houseVo.getApart())) {
                house.setApartFilter(stringToInt.lastNum(houseVo.getApart()));
            }
        } else {
            if (!StringUtils.isEmpty(houseVo.getApart())) {
                house.setApartFilter(stringToInt.firstNum(houseVo.getApart()));
            }
        }
        house.setArea(houseVo.getArea());
        house.setCity(houseVo.getCity());
        house.setContact(houseVo.getContact());
        house.setFloorArea(houseVo.getFloorArea());
        if (!StringUtils.isEmpty(houseVo.getHandTime())) {
            house.setHandTime(dateUtils.cnToDate(houseVo.getHandTime()));
        }
        house.setHouseName(houseVo.getHouseName());
        house.setIsBus(houseVo.getIsBus());
        house.setIsFree(houseVo.getIsFree());
        house.setIsHome(houseVo.getIsHome());
        house.setIsMinprice(houseVo.getIsMinprice());
        house.setIsMintotalprice(houseVo.getIsMintotalprice());
        house.setIsPark(houseVo.getIsPark());
        house.setIsSubway(houseVo.getIsSubway());
        house.setLeaseWay(houseVo.getLeaseWay());
        house.setMainFloor(houseVo.getMainFloor());
        if (!StringUtils.isEmpty(houseVo.getOpenTime())) {
            house.setOpenTime(dateUtils.cnToDate(houseVo.getOpenTime()));
        }
        house.setQuality(houseVo.getQuality());
        house.setPayWay(houseVo.getPayWay());
        house.setPhone(houseVo.getPhone());
        Map map = new HashMap();
        map.put("picUrl", Arrays.asList(houseVo.getPicUrl()));
        log.info("test:{}" + Arrays.asList(houseVo.getPicUrl()));
        String data = JSON.toJSONString(map.get("picUrl"));
        house.setPicUrl(data);
        house.setPrice(houseVo.getPrice());
        house.setProvince(houseVo.getProvince());
        house.setRemark(houseVo.getRemark());
        house.setRenovation(houseVo.getRenovation());
        house.setSeeHouse(houseVo.getSeeHouse());
        house.setState(houseVo.getState());
        house.setDirection(houseVo.getDirection());
        house.setTags(houseVo.getTags());
        house.setQuality(houseVo.getQuality());
        house.setFloors(houseVo.getFloors());
        house.setTitle(houseVo.getTitle());
        house.setTotalPrice(houseVo.getTotalPrice());
        if (!StringUtils.isEmpty(houseVo.getTotalPrice())) {
            ResultReturn resultReturn = new ResultReturn();
            house.setTotalPriceFilter(resultReturn.getResult(houseVo.getTotalPrice()));
        }
        house.setType(houseVo.getType());
       /* if(houseVo.getType() ==1){
            house.setApartFilter(numUtils.newHouse());
        }*/
        house.setHouseYear(houseVo.getHouseYear());
        house.setDeploy(houseVo.getDeploy());
        house.setIsRecommend(houseVo.getIsRecommend());
        houseService.insertSelective(house);
        House house1 = houseService.selectByPrimaryKey(house);
        log.info("添加后第一次查询数据{}", house1.toString());
        if (!StringUtils.isEmpty(houseVo.getMainApart())) {
            for (MainApartVO apart : houseVo.getMainApart()) {
                MainApart mainApart = new MainApart();
                mainApart.setHouseId(house1.getId());
                mainApart.setApart(apart.getApart());
                mainApart.setMainArea(apart.getMainArea());
                mainApart.setPicUrl(apart.getPicUrl());
                mainApart.setState(apart.getState());
                houseService.insertByMainApart(mainApart);
            }
        }
        for (AgentDTO agent : houseVo.getAgent()) {
            RHouse rHouse = new RHouse();
            if (!StringUtils.isEmpty(agent.getId())) {
                rHouse.setBusId(agent.getBusId());
                rHouse.setHouseId(house1.getId());
                rHouse.setAgentId(agent.getId());
                houseService.insertByAgent(rHouse);
            } else {
                continue;
            }
        }

        ReturnHouse house2 = new ReturnHouse();
        Integer idd = house1.getId();
        System.out.println("新增的数据id" + idd);
        List<RHouse> rHouseList = houseService.selectByList(house1.getId());
        List list = new ArrayList();
        for (RHouse rHouse : rHouseList) {
            if (StringUtils.isEmpty(rHouse.getAgentId())) {
                continue;
            } else {
                Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                if (StringUtils.isEmpty(agent))
                    continue;
                list.add(agent);
            }
        }
        List<MainApart> mainAparts = houseService.selectByMainApartAndhouseId(house1.getId());
        List list1 = new ArrayList();
        if (mainAparts != null && mainAparts.size() > 0) {
            for (MainApart apart : mainAparts) {
                MainApartVO mainApartVO = new MainApartVO();
                mainApartVO.setApart(apart.getApart());
                mainApartVO.setMainArea(apart.getMainArea());
                mainApartVO.setPicUrl(apart.getPicUrl());
                mainApartVO.setState(apart.getState());
                list1.add(mainApartVO);
            }
        }
        house2.setMainApart(list1);

        Integer record = historyService.selectByRecordCount(house1.getId());
        if (!StringUtils.isEmpty(record)) {
            house2.setRecord(record);
        } else {
            house2.setRecord(0);
        }
        house2.setCollectCount(0);
        house2.setActualFloor(house1.getActualFloor());
        house2.setAddress(house1.getAddress());
        house2.setApart(house1.getApart());
        house2.setArea(house1.getArea());
        house2.setCity(house1.getCity());
        house2.setContact(house1.getContact());
        house2.setFloorArea(house1.getFloorArea());
        if (!StringUtils.isEmpty(house1.getHandTime())) {
            house2.setHandTime(dateUtils.dateToString(house1.getHandTime()));
        }
        house2.setHouseName(house1.getHouseName());
        house2.setIsBus(house1.getIsBus());
        house2.setIsFree(house1.getIsFree());
        house2.setIsHome(house1.getIsHome());
        house2.setDeploy(house1.getDeploy());
        house2.setIsMinprice(house1.getIsMinprice());
        house2.setIsMintotalprice(house1.getIsMintotalprice());
        house2.setIsPark(house1.getIsPark());
        house2.setIsSubway(house1.getIsSubway());
        house2.setLeaseWay(house1.getLeaseWay());
        house2.setMainFloor(house1.getMainFloor());
        if (!StringUtils.isEmpty(house1.getOpenTime())) {
            house2.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
        }
        house2.setQuality(house1.getQuality());
        house2.setPayWay(house1.getPayWay());
        house2.setPhone(house1.getPhone());
        house2.setPrice(house1.getPrice());
        house2.setProvince(house1.getProvince());
        house2.setRemark(house1.getRemark());
        house2.setRenovation(house1.getRenovation());
        house2.setSeeHouse(house1.getSeeHouse());
        house2.setState(house1.getState());
        house2.setTags(house1.getTags());
        house2.setMinfloorArea(house1.getMinFloorarea());
        house2.setMaxfloorArea(house1.getMaxFloorarea());
        house2.setQuality(house1.getQuality());
        house2.setFloors(house1.getFloors());
        house2.setTitle(house1.getTitle());
        house2.setTotalPrice(house1.getTotalPrice());
        house2.setType(house1.getType());
        house2.setHouseYear(house1.getHouseYear());
        house2.setDirection(house1.getDirection());
        house2.setState(house1.getState());
        house2.setId(house1.getId());
        house2.setCreateTime(house1.getCreateTime());
        house2.setIsRecommend(house1.getIsRecommend());
        String str3 = house1.getPicUrl();
        if (!StringUtils.isEmpty(str3)) {
            str3 = str3.replaceAll("\\[", "");
            str3 = str3.replaceAll("\\]", "");
            str3 = str3.replaceAll("\\\"", "");
            log.info(str3);
            String[] arr2 = new String[0];
            System.out.println("Str3" + str3.length());
            if (str3.length() > 0) {
                String str4 = str3.substring(0, str3.length());
                log.info(str4);
                String[] arr = str4.split(",");
                house2.setPicUrl(arr);
            } else {
                house2.setPicUrl(arr2);
            }
        }
        log.info("添加后第二次查询数据{}", house2.toString());
        if (StringUtils.isEmpty(list)) {
            result.setSuccess(true);
            result.setMessage("添加成功");
            result.setData(house2);
        } else {
            house2.setAgent(list);
            result.setSuccess(true);
            result.setMessage("添加成功");
            result.setData(house2);
        }
        return result;
    }

    //房屋信息删除（逻辑删除）
    @ApiOperation("房屋信息删除（逻辑删除）")
    @PostMapping("/api/house/delete")
    public Result delete(@RequestBody DelHouseId houseId) {
        Result result = new Result();
        if (StringUtils.isEmpty(houseId.getId())) {
            result.setSuccess(false);
            result.setMessage("请输入房源编号");
        } else {
            RHouse rHouse = new RHouse();
            rHouse.setHouseId(houseId.getId());
            List<RHouse> rHouses = houseService.selectByList(rHouse.getHouseId());
            for (RHouse rHouse1 : rHouses) {
                houseService.deleteByRhouse(rHouse1.getId());
            }
            List<Collect> collectList = collectService.selectByHouseCollectId(houseId.getId());
            if (!StringUtils.isEmpty(collectList)) {
                for (Collect collect : collectList) {
                    collectService.deleteByPrimaryKey(collect.getId());
                }
            }
            List<History> historyList = historyService.selectByHouseHistoryId(houseId.getId());
            if (!StringUtils.isEmpty(historyList)) {
                for (History history : historyList) {
                    historyService.deleteByPrimaryKey(history.getId());
                }
            }
            House house = new House();
            house.setId(houseId.getId());
            houseService.updateIsdel(house);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }
        return result;

    }

    @ApiOperation("修改房源信息")
    @PostMapping("/api/house/update")
    public Result update(@RequestBody UpdateHouseVo house) throws Exception {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        ResultReturn resultReturn = new ResultReturn();
        StringToInt stringToInt = new StringToInt();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("获取修改房源的数据：{}", house);
        if (StringUtils.isEmpty(house.getId())) {
            result.setSuccess(false);
            result.setMessage("入参错误");
        } else {
            List<RHouse> rHouses = houseService.selectByList(house.getId());
            if (rHouses != null && rHouses.size() > 0) {
                for (RHouse rHouse : rHouses) {
                    if (StringUtils.isEmpty(rHouse)) {
                        continue;
                    }
                    houseService.deleteByHouseIdAndAgentId(rHouse);
                    log.info("删除成功：", rHouse);
                }

            }
            log.info("修改房源获取经纪人的数据：{}", house.getAgent());
            for (AgentDTO agent : house.getAgent()) {
                RHouse rHouse = new RHouse();
                rHouse.setHouseId(house.getId());
                rHouse.setBusId(agent.getBusId());
                rHouse.setAgentId(agent.getId());
                houseService.insertByAgent(rHouse);
            }
            List<MainApart> mainAparts = houseService.selectByMainApartAndhouseId(house.getId());
            if (!StringUtils.isEmpty(mainAparts)) {
                for (MainApart mainApart : mainAparts) {
                    houseService.deleteByMainApart(mainApart.getId());
                    log.info("删除成功");
                }
            }
            if (!StringUtils.isEmpty(house.getMainApart())) {
                for (MainApartVO mainApartVO : house.getMainApart()) {
                    MainApart mainApart = new MainApart();
                    mainApart.setPicUrl(mainApartVO.getPicUrl());
                    mainApart.setHouseId(house.getId());
                    mainApart.setMainArea(mainApartVO.getMainArea());
                    mainApart.setApart(mainApartVO.getApart());
                    mainApart.setState(mainApartVO.getState());
                    houseService.insertByMainApart(mainApart);
                }
            }
            House house2 = new House();
            house2.setIsRecommend(house.getIsRecommend());
            //house2.setCreateTime(df.parse(df.format(new Date())));
            house2.setActualFloor(house.getActualFloor());
            house2.setAddress(house.getAddress());
            house2.setApart(house.getApart());
            if (house.getType().equals(1)) {
                if (!StringUtils.isEmpty(house.getApart())) {
                    house2.setApartFilter(stringToInt.lastNum(house.getApart()));
                }
            } else {
                if (!StringUtils.isEmpty(house.getApart())) {
                    house2.setApartFilter(stringToInt.firstNum(house.getApart()));
                }
            }
            house2.setArea(house.getArea());
            house2.setCity(house.getCity());
            house2.setDeploy(house.getDeploy());
            house2.setContact(house.getContact());
            house2.setDirection(house.getDirection());
            house2.setFloorArea(house.getFloorArea());
            if (!StringUtils.isEmpty(house.getHandTime())) {
                house2.setHandTime(dateUtils.cnToDate(house.getHandTime()));
            }
            house2.setHouseName(house.getHouseName());
            house2.setIsBus(house.getIsBus());
            house2.setIsFree(house.getIsFree());
            house2.setIsHome(house.getIsHome());
            house2.setIsMinprice(house.getIsMinprice());
            house2.setIsMintotalprice(house.getIsMintotalprice());
            house2.setIsPark(house.getIsPark());
            house2.setMinFloorarea(house.getMinfloorArea());
            house2.setMaxFloorarea(house.getMaxfloorArea());
            house2.setIsSubway(house.getIsSubway());
            house2.setLeaseWay(house.getLeaseWay());
            house2.setMainFloor(house.getMainFloor());
            if (!StringUtils.isEmpty(house.getOpenTime())) {
                house2.setOpenTime(dateUtils.cnToDate(house.getOpenTime()));
            }
            house2.setQuality(house.getQuality());
            house2.setPayWay(house.getPayWay());
            house2.setPhone(house.getPhone());
            Map map1 = new HashMap();

            map1.put("picUrl", Arrays.asList(house.getPicUrl()));
            String dataMap = JSON.toJSONString(map1.get("picUrl"));
            house2.setPicUrl(dataMap);
            house2.setPrice(house.getPrice());
            house2.setProvince(house.getProvince());
            house2.setRemark(house.getRemark());
            house2.setRenovation(house.getRenovation());
            house2.setSeeHouse(house.getSeeHouse());
            house2.setState(house.getState());
            house2.setTags(house.getTags());
            house2.setQuality(house.getQuality());
            house2.setFloors(house.getFloors());
            house2.setTitle(house.getTitle());
            house2.setTotalPrice(house.getTotalPrice());
            if (!StringUtils.isEmpty(house.getTotalPrice())) {
                house2.setTotalPriceFilter(resultReturn.getResult(house.getTotalPrice()));
            }
            house2.setType(house.getType());
            house2.setHouseYear(house.getHouseYear());
            house2.setId(house.getId());
            houseService.updateByPrimaryKeySelective(house2);
            House house1 = houseService.selectByPrimaryKey(house2);
            log.info("修改后第一次查询的数据:{}", house1.toString());
            if (StringUtils.isEmpty(house1)) {
                result.setMessage("未查到该房源，修改失败");
                result.setSuccess(false);
            } else {

                ReturnHouse house3 = new ReturnHouse();
                Integer record = historyService.selectByRecordCount(house1.getId());
                log.info("获取浏览数据:{}", record);
                if (!StringUtils.isEmpty(record)) {
                    house3.setRecord(record);
                } else {
                    house3.setRecord(0);
                }
                Integer collectCount = collectService.queryByHouseCollect(house1.getId());
                if (StringUtils.isEmpty(collectCount)) {
                    house3.setCollectCount(0);
                } else {
                    house3.setCollectCount(collectCount);
                }
                house3.setIsRecommend(house1.getIsRecommend());
                house3.setMaxfloorArea(house1.getMaxFloorarea());
                house3.setMinfloorArea(house1.getMinFloorarea());
                house3.setActualFloor(house1.getActualFloor());
                house3.setAddress(house1.getAddress());
                house3.setDirection(house1.getDirection());
                house3.setState(house1.getState());
                house3.setApart(house1.getApart());
                house3.setArea(house1.getArea());
                house3.setCity(house1.getCity());
                house3.setContact(house1.getContact());
                house3.setFloorArea(house1.getFloorArea());
                if (!StringUtils.isEmpty(house1.getHandTime())) {
                    house3.setHandTime(dateUtils.dateToString(house1.getHandTime()));
                }
                house3.setHouseName(house1.getHouseName());
                house3.setIsBus(house1.getIsBus());
                house3.setIsFree(house1.getIsFree());
                house3.setIsHome(house1.getIsHome());
                house3.setIsMinprice(house1.getIsMinprice());
                house3.setIsMintotalprice(house1.getIsMintotalprice());
                house3.setIsPark(house1.getIsPark());
                house3.setIsSubway(house1.getIsSubway());
                house3.setLeaseWay(house1.getLeaseWay());
                house3.setMainFloor(house1.getMainFloor());
                if (!StringUtils.isEmpty(house1.getOpenTime())) {
                    house3.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
                }
                house3.setQuality(house1.getQuality());
                house3.setDeploy(house1.getDeploy());
                house3.setPayWay(house1.getPayWay());
                house3.setPhone(house1.getPhone());
                String str3 = house1.getPicUrl();
                String[] arr2 = new String[0];
                str3 = str3.replaceAll("\\[", "");
                str3 = str3.replaceAll("\\]", "");
                str3 = str3.replaceAll("\\\"", "");
                log.info(str3);
                if (str3.length() > 0) {
                    String str4 = str3.substring(0, str3.length());
                    log.info(str4);
                    String[] arr = str4.split(",");
                    house3.setPicUrl(arr);
                } else {
                    house3.setPicUrl(arr2);
                }
                house3.setPrice(house1.getPrice());
                house3.setProvince(house1.getProvince());
                house3.setRemark(house1.getRemark());
                house3.setRenovation(house1.getRenovation());
                house3.setSeeHouse(house1.getSeeHouse());
                house3.setState(house1.getState());
                house3.setTags(house1.getTags());
                house3.setQuality(house1.getQuality());
                house3.setFloors(house1.getFloors());
                house3.setTitle(house1.getTitle());
                house3.setTotalPrice(house1.getTotalPrice());
                house3.setType(house1.getType());
                house3.setHouseYear(house1.getHouseYear());
                house3.setId(house1.getId());
                log.info("修改后返回的数据{}", house3.toString());
                List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house1.getId());
                List list1 = new ArrayList();
                if (mainApartList != null && mainApartList.size() > 0) {
                    for (MainApart apart : mainApartList) {
                        MainApartVO mainApartVO = new MainApartVO();
                        mainApartVO.setApart(apart.getApart());
                        mainApartVO.setMainArea(apart.getMainArea());
                        mainApartVO.setPicUrl(apart.getPicUrl());
                        mainApartVO.setState(apart.getState());
                        list1.add(mainApartVO);
                    }
                }
                List<RHouse> rHouseList = houseService.selectByList(house1.getId());
                List list = new ArrayList();
                if (rHouseList != null) {
                    for (RHouse rHouse : rHouseList) {
                        Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                        if (StringUtils.isEmpty(agent))
                            continue;
                        list.add(agent);
                    }
                    house3.setMainApart(list1);
                    house3.setAgent(list);
                    result.setSuccess(true);
                    result.setMessage("修改成功");
                    result.setData(house3);
                } else {
                    result.setMessage("暂无经纪人关注");
                    result.setSuccess(false);
                }

            }
        }
        return result;
    }


    //分页查询
    @ApiOperation("分页查询")
    @PostMapping(value = "/api/house/select")
    public Result select(@RequestBody QueryDto jsonParam) throws Exception {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        StringToInt stringToInt = new StringToInt();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = null;
        String d2 = null;
        //获取pageSize数据的pageNum条的数据，默认查询总数
        if (!StringUtils.isEmpty(jsonParam.getBeginTime())) {
            Date date1 = dateUtils.stringToDate(jsonParam.getBeginTime());
            d1 = dateFormat.format(date1);
            jsonParam.setBeginTime(d1);
        }
        if (!StringUtils.isEmpty(jsonParam.getEndTime())) {
            Date date2 = dateUtils.stringToDate(jsonParam.getEndTime());
            d2 = dateFormat.format(date2);
            jsonParam.setEndTime(d2);
        }
        if (jsonParam.getType().equals(1)) {
            if (!StringUtils.isEmpty(jsonParam.getApart())) {
                Map newmap = stringToInt.chToString(jsonParam.getApart());
                if (!StringUtils.isEmpty(newmap.get("apart"))) {
                    jsonParam.setApart((String) newmap.get("apart"));
                }
                if (!StringUtils.isEmpty(newmap.get("apartFilter"))) {
                    jsonParam.setApartFilter((Integer) newmap.get("apartFilter"));
                }
                log.info("整合后的查询条件：{}", newmap.get("apart"));
            }
        } else {
            if (!StringUtils.isEmpty(jsonParam.getApart())) {
                Map newmap = stringToInt.chToString1(jsonParam.getApart());
                if (!StringUtils.isEmpty(newmap.get("apart"))) {
                    jsonParam.setApart((String) newmap.get("apart"));
                }
                if (!StringUtils.isEmpty(newmap.get("apartFilter"))) {
                    jsonParam.setApartFilter((Integer) newmap.get("apartFilter"));
                }
                log.info("整合后的查询条件：{}", newmap.get("apart"));
            }
        }
        if (!StringUtils.isEmpty(jsonParam.getSortKey())) {
            if (jsonParam.getSortKey().equals("price")) {
                jsonParam.setSortKey("CONVERT (price , DECIMAL)");
            } else if (jsonParam.getSortKey().equals("totalPrice")) {
                jsonParam.setSortKey("CONVERT (total_price , DECIMAL)");
            } else if (jsonParam.getSortKey().equals("floorArea")) {
                jsonParam.setSortKey("CONVERT (floor_area , DECIMAL)");
            } else if (jsonParam.getSortKey().equals("minFloorarea")) {
                jsonParam.setSortKey("CONVERT (min_floorarea , DECIMAL)");
            } else if (jsonParam.getSortKey().equals("createTime")) {
                jsonParam.setSortKey("create_time");
            }
        }
        Page page = PageHelper.startPage(jsonParam.getPageIndex(), jsonParam.getPageSize());
        //紧跟着是一个查询语句会被分页，第二个查询语句不会被分页，除非再次调用pegeHelper
        List<House> houseList = houseService.houseList(jsonParam);
        PageInfo info = new PageInfo<>(page.getResult());
        if (!StringUtils.isEmpty(houseList)) {
            List list = new ArrayList<Map<String, Object>>();
            for (House house : houseList) {
                ReturnHouse house3 = new ReturnHouse();
                Integer record = historyService.selectByRecordCount(house.getId());
                if (!StringUtils.isEmpty(record)) {
                    house3.setRecord(record);
                } else {
                    house3.setRecord(0);
                }
                Integer collectCount = collectService.queryByHouseCollect(house.getId());
                if (StringUtils.isEmpty(collectCount)) {
                    house3.setCollectCount(0);
                } else {
                    house3.setCollectCount(collectCount);
                }
                if (jsonParam.getPhone() != null && jsonParam.getPhone().length() == 11) {
                    Collect collect1 = new Collect();
                    collect1.setPhone(jsonParam.getPhone());
                    collect1.setHouseId(house.getId());
                    Collect collect = collectService.selectByCollectHouse(collect1);
                    if (!StringUtils.isEmpty(collect)) {
                        house3.setIsFollow(collect.getState());
                    } else {
                        house3.setIsFollow(0);
                    }
                } else {
                    house3.setIsFollow(0);
                }
                house3.setActualFloor(house.getActualFloor());
                house3.setAddress(house.getAddress());
                house3.setCreateTime(house.getCreateTime());
                house3.setApart(house.getApart());
                house3.setIsRecommend(house.getIsRecommend());
                house3.setArea(house.getArea());
                house3.setCity(house.getCity());
                house3.setContact(house.getContact());
                house3.setFloorArea(house.getFloorArea());
                if (!StringUtils.isEmpty(house.getHandTime())) {
                    house3.setHandTime(dateUtils.dateToString(house.getHandTime()));
                }
                house3.setHouseName(house.getHouseName());
                house3.setIsBus(house.getIsBus());
                house3.setIsFree(house.getIsFree());
                house3.setIsHome(house.getIsHome());
                house3.setIsMinprice(house.getIsMinprice());
                house3.setIsMintotalprice(house.getIsMintotalprice());
                house3.setIsPark(house.getIsPark());
                house3.setIsSubway(house.getIsSubway());
                house3.setLeaseWay(house.getLeaseWay());
                house3.setMainFloor(house.getMainFloor());
                if (!StringUtils.isEmpty(house.getOpenTime())) {
                    house3.setOpenTime(dateUtils.dateToString(house.getOpenTime()));
                }
                house3.setQuality(house.getQuality());
                house3.setDeploy(house.getDeploy());
                house3.setPayWay(house.getPayWay());
                house3.setPhone(house.getPhone());
                String str3 = house.getPicUrl();
                if (!StringUtils.isEmpty(str3)) {
                    String[] arr2 = new String[0];
                    str3 = str3.replaceAll("\\[", "");
                    str3 = str3.replaceAll("\\]", "");
                    str3 = str3.replaceAll("\\\"", "");
                    log.info(str3);
                    if (str3.length() > 0) {
                        String str4 = str3.substring(0, str3.length());
                        log.info(str4);
                        String[] arr = str4.split(",");
                        house3.setPicUrl(arr);
                    } else {
                        house3.setPicUrl(arr2);
                    }
                } else {
                    String[] arr3 = new String[0];
                    house3.setPicUrl(arr3);
                }
                List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house.getId());
                List list2 = new ArrayList();
                if (mainApartList != null && mainApartList.size() > 0) {
                    for (MainApart apart : mainApartList) {
                        MainApartVO mainApartVO = new MainApartVO();
                        mainApartVO.setApart(apart.getApart());
                        mainApartVO.setMainArea(apart.getMainArea());
                        mainApartVO.setPicUrl(apart.getPicUrl());
                        mainApartVO.setState(apart.getState());
                        list2.add(mainApartVO);
                    }
                }
                house3.setMainApart(list2);
                house3.setPrice(house.getPrice());
                house3.setProvince(house.getProvince());
                house3.setRemark(house.getRemark());
                house3.setRenovation(house.getRenovation());
                house3.setSeeHouse(house.getSeeHouse());
                house3.setState(house.getState());
                house3.setTags(house.getTags());
                house3.setQuality(house.getQuality());
                house3.setFloors(house.getFloors());
                house3.setMaxfloorArea(house.getMaxFloorarea());
                house3.setMinfloorArea(house.getMinFloorarea());
                house3.setTitle(house.getTitle());
                house3.setTotalPrice(house.getTotalPrice());
                house3.setType(house.getType());
                house3.setHouseYear(house.getHouseYear());
                house3.setDirection(house.getDirection());
                house3.setId(house.getId());
                List<RHouse> rHouseList1 = houseService.selectByDistinct(house.getId());
                if (!StringUtils.isEmpty(rHouseList1) && rHouseList1.size() > 0) {
                    for (RHouse rHouse1 : rHouseList1) {
                        Business business = new Business();
                        business.setId(rHouse1.getBusId());
                        Business business1 = businessService.selectByPrimaryKey(business);
                        if (StringUtils.isEmpty(business1))
                            continue;
                        house3.setBusiness(business1);
                    }
                }
                List<RHouse> rHouseList2 = houseService.selectByDistinct(house.getId());
                if (!StringUtils.isEmpty(rHouseList2) && rHouseList2.size() > 0) {
                    for (RHouse rHouse1 : rHouseList2) {
                        Business business = new Business();
                        business.setId(rHouse1.getBusId());
                        Business business1 = businessService.selectByPrimaryKey(business);
                        if (StringUtils.isEmpty(business1))
                            continue;
                        house3.setBusiness(business1);
                    }
                }
                List<RHouse> rHouseList = houseService.selectByList(house.getId());
                List list1 = new ArrayList();
                if (rHouseList != null && rHouseList.size() > 0) {
                    for (RHouse rHouse : rHouseList) {
                        Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                        if (StringUtils.isEmpty(agent))
                            continue;
                        list1.add(agent);
                    }
                    house3.setAgent(list1);
                }
                list.add(house3);
            }

            log.info("zongshuju{}", info.toString());
            Map map = new HashMap();
            map.put("rows", list);
            map.put("totalCount", info.getTotal());
            map.put("pageIndex", jsonParam.getPageIndex());
            map.put("pageSize", jsonParam.getPageSize());
            result.setSuccess(true);
            result.setMessage("查询成功");
            result.setData(map);
        } else {
            Map map = new HashMap();
            List list = new ArrayList();
            map.put("rows", list);
            map.put("totalCount", info.getTotal());
            map.put("pageIndex", jsonParam.getPageIndex());
            map.put("pageSize", jsonParam.getPageSize());
            result.setSuccess(true);
            result.setMessage("暂无数据");
        }
        return result;
    }

    //单条数据查询房源详情(用户使用)
    @ApiOperation("单条数据查询房源详情(用户使用)")
    @PostMapping("/api/house/detail/select")
    public Result selectByCode(@RequestBody HUVO huvo) throws Exception {
        Result result = new Result();
        User user = new User();
        House house = new House();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        house.setId(huvo.getId());
        String phone = huvo.getPhone();
        if (StringUtils.isEmpty(phone)) {
            user.setPhone("200");
        } else {
            user.setPhone(phone);
        }
        DateUtils dateUtils = new DateUtils();

        User user1 = userService.selectByPhoneNo(user);
        if (!StringUtils.isEmpty(user1)) {
            Map map = new HashMap();
            map.put("houseId", huvo.getId());
            if (!StringUtils.isEmpty(user1)) {
                map.put("openId", user1.getOpenId());
            }
            House house1 = houseService.selectByPrimaryKey(house);
            List<RHouse> rHouse = houseService.selectByDistinct(huvo.getId());
            if (!StringUtils.isEmpty(rHouse)) {
                if (!StringUtils.isEmpty(house1)) {
                    History history1 = new History();
                    for (RHouse rHouse1 : rHouse) {
                        history1.setCreateTime(df.parse(df.format(new Date())));

                        if (!StringUtils.isEmpty(user1.getOpenId())) {
                            history1.setOpenId(user1.getOpenId());
                        }
                        history1.setPhone(user1.getPhone());
                        history1.setHouseId(huvo.getId());
                        history1.setBusId(rHouse1.getBusId());
                        History history = historyService.selectByOpenIdAndHouseId(map);
                        if (StringUtils.isEmpty(history)) {
                            historyService.insertSelective(history1);
                        } else {
                            historyService.updateByCreateTime(history1);
                        }
                    }
                    ReturnHouse house3 = new ReturnHouse();
                    Integer record = historyService.selectByRecordCount(house1.getId());
                    if (!StringUtils.isEmpty(record)) {
                        house3.setRecord(record);
                    } else {
                        house3.setRecord(0);
                    }
                    Integer collectCount = collectService.queryByHouseCollect(house1.getId());
                    if (StringUtils.isEmpty(collectCount)) {
                        house3.setCollectCount(0);
                    } else {
                        house3.setCollectCount(collectCount);
                    }
                    if (user1.getPhone().length() == 11) {
                        Collect collect1 = new Collect();
                        collect1.setPhone(user1.getPhone());
                        collect1.setHouseId(house1.getId());
                        Collect collect = collectService.selectByCollectHouse(collect1);
                        if (!StringUtils.isEmpty(collect)) {
                            house3.setIsFollow(collect.getState());
                        } else {
                            house3.setIsFollow(0);
                        }
                    } else {
                        house3.setIsFollow(0);
                    }
                    house3.setCreateTime(house1.getCreateTime());
                    house3.setIsRecommend(house1.getIsRecommend());
                    house3.setActualFloor(house1.getActualFloor());
                    house3.setAddress(house1.getAddress());
                    house3.setApart(house1.getApart());
                    house3.setArea(house1.getArea());
                    house3.setCity(house1.getCity());
                    house3.setDirection(house1.getDirection());
                    house3.setContact(house1.getContact());
                    house3.setFloorArea(house1.getFloorArea());
                    if (!StringUtils.isEmpty(house1.getHandTime())) {
                        house3.setHandTime(dateUtils.dateToString(house1.getHandTime()));
                    }
                    if (!StringUtils.isEmpty(house1.getOpenTime())) {
                        house3.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
                    }
                    house3.setHouseName(house1.getHouseName());
                    house3.setIsBus(house1.getIsBus());
                    house3.setIsFree(house1.getIsFree());
                    house3.setIsHome(house1.getIsHome());
                    house3.setDeploy(house1.getDeploy());
                    house3.setIsMinprice(house1.getIsMinprice());
                    house3.setIsMintotalprice(house1.getIsMintotalprice());
                    house3.setIsPark(house1.getIsPark());
                    house3.setIsSubway(house1.getIsSubway());
                    house3.setMinfloorArea(house1.getMinFloorarea());
                    house3.setMaxfloorArea(house1.getMaxFloorarea());
                    house3.setLeaseWay(house1.getLeaseWay());
                    house3.setMainFloor(house1.getMainFloor());

                    house3.setQuality(house1.getQuality());
                    house3.setPayWay(house1.getPayWay());
                    house3.setPhone(house1.getPhone());
                    String str3 = house1.getPicUrl();
                    String[] arr2 = new String[0];
                    str3 = str3.replaceAll("\\[", "");
                    str3 = str3.replaceAll("\\]", "");
                    str3 = str3.replaceAll("\\\"", "");
                    log.info(str3);
                    if (str3.length() > 0) {
                        String str4 = str3.substring(0, str3.length());
                        log.info(str4);
                        String[] arr = str4.split(",");
                        house3.setPicUrl(arr);
                    } else {
                        house3.setPicUrl(arr2);
                    }
                    house3.setProvince(house1.getProvince());
                    house3.setRemark(house1.getRemark());
                    house3.setRenovation(house1.getRenovation());
                    house3.setSeeHouse(house1.getSeeHouse());
                    house3.setState(house1.getState());
                    house3.setTags(house1.getTags());
                    house3.setQuality(house1.getQuality());
                    house3.setFloors(house1.getFloors());
                    house3.setTitle(house1.getTitle());
                    house3.setTotalPrice(house1.getTotalPrice());
                    house3.setType(house1.getType());
                    house3.setPrice(house1.getPrice());
                    house3.setHouseYear(house1.getHouseYear());
                    house3.setId(house1.getId());
                    List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house1.getId());
                    List list2 = new ArrayList();
                    if (mainApartList != null && mainApartList.size() > 0) {
                        for (MainApart apart : mainApartList) {
                            MainApartVO mainApartVO = new MainApartVO();
                            mainApartVO.setApart(apart.getApart());
                            mainApartVO.setMainArea(apart.getMainArea());
                            mainApartVO.setPicUrl(apart.getPicUrl());
                            mainApartVO.setState(apart.getState());
                            list2.add(mainApartVO);
                        }
                    }
                    house3.setMainApart(list2);
                    List<RHouse> rHouseList1 = houseService.selectByDistinct(house1.getId());
                    if (!StringUtils.isEmpty(rHouseList1) && rHouseList1.size() > 0) {
                        for (RHouse rHouse1 : rHouseList1) {
                            Business business = new Business();
                            business.setId(rHouse1.getBusId());
                            Business business1 = businessService.selectByPrimaryKey(business);
                            if (StringUtils.isEmpty(business1))
                                continue;
                            house3.setBusiness(business1);
                        }

                    }
                    List<RHouse> rHouseList = houseService.selectByList(house1.getId());
                    List list = new ArrayList();
                    if (rHouseList != null && rHouseList.size() > 0) {
                        for (RHouse rHouse1 : rHouseList) {
                            Agent agent = agentService.selectByAgentId(rHouse1.getAgentId());
                            if (StringUtils.isEmpty(agent))
                                continue;
                            list.add(agent);
                        }
                        house3.setAgent(list);
                        result.setData(house3);
                        result.setMessage("chaxunchenggong");
                        result.setSuccess(true);
                    } else {
                        result.setData(house3);
                        result.setMessage("chaxunchenggong");
                        result.setSuccess(true);
                    }
                } else {
                    result.setSuccess(true);
                    result.setMessage("暂无该房源详细信息");
                }

            } else {
                result.setSuccess(true);
                result.setMessage("暂无商家信息");
            }
        } else {
            result.setSuccess(false);
            result.setMessage("该用户暂未授权");
        }
        return result;
    }

    @ApiOperation("查询该房源下所有的经纪人")
    @PostMapping("/api/house/selectByAgent")
    public Result selectByAgent(@RequestBody HouseId houseId) {
        Result result = new Result();

        if (StringUtils.isEmpty(houseId.getId())) {
            result.setMessage("入参错误");
            result.setSuccess(false);
        } else {
            List<RHouse> rHouses = houseService.selectByList(houseId.getId());
            if (StringUtils.isEmpty(rHouses)) {
                result.setMessage("该房源暂无经纪人关注");
                result.setSuccess(false);
            } else {
                House house1 = houseService.selectByHouseId(houseId.getId());
                if (!StringUtils.isEmpty(house1)) {
                    List list = new ArrayList();
                    for (RHouse rHouse : rHouses) {
                        Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                        if (StringUtils.isEmpty(agent)) {
                            continue;
                        }
                        list.add(agent);
                    }
                    house1.setAgent(list);
                    result.setSuccess(true);
                    result.setMessage("查询成功");
                    result.setData(house1);
                } else {
                    result.setSuccess(false);
                    result.setMessage("暂无该房源信息");
                }
            }

        }
        return result;

    }

    /*<!--根据房屋类型统计数据（type1:新房，2:二手房，3:租房）-->*/
    @ApiOperation("根据房屋类型统计数据（type1:新房，2:二手房，3:租房）")
    @PostMapping("/api/house/total")
    public Result total(@RequestBody ID id) {
        Result result = new Result();
        if (StringUtils.isEmpty(id)) {
            result.setSuccess(false);
            result.setMessage("入参出错");
        } else {
            List list = new ArrayList();
            Integer total = houseService.selectByType1(id.getBusId());
            TotalVo totalVo1 = new TotalVo();
            if (StringUtils.isEmpty(total)) {
                totalVo1.setLabel("新房");
                totalVo1.setTotalCount(0);
            } else {
                totalVo1.setLabel("新房");
                totalVo1.setTotalCount(total);
            }
            Integer total2 = houseService.selectByType2(id.getBusId());
            TotalVo totalVo2 = new TotalVo();
            if (StringUtils.isEmpty(total2)) {
                totalVo2.setLabel("二手房");
                totalVo2.setTotalCount(0);
            } else {
                totalVo2.setLabel("二手房");
                totalVo2.setTotalCount(total2);
            }
            Integer total3 = houseService.selectByType3(id.getBusId());
            TotalVo totalVo3 = new TotalVo();
            if (StringUtils.isEmpty(total3)) {
                totalVo3.setLabel("租房");
                totalVo3.setTotalCount(0);
            } else {
                totalVo3.setLabel("租房");
                totalVo3.setTotalCount(total3);
            }

            Integer browseSum = historyService.selectByBrowseCount(id.getBusId());
            /* Integer browseSum = historyService.selectByCount(id.getBusId());*/
            TotalVo totalVo4 = new TotalVo();
            if (StringUtils.isEmpty(browseSum)) {
                totalVo4.setLabel("用户浏览");
                totalVo4.setTotalCount(0);
            } else {
                totalVo4.setLabel("用户浏览");
                totalVo4.setTotalCount(browseSum);
            }
            Integer collectCount = collectService.selectByBusCount(id.getBusId());
            TotalVo totalVo5 = new TotalVo();
            if (StringUtils.isEmpty(collectCount)) {
                totalVo5.setLabel("用户关注");
                totalVo5.setTotalCount(0);
            } else {
                totalVo5.setLabel("用户关注");
                totalVo5.setTotalCount(collectCount);
            }
            list.add(totalVo1);
            list.add(totalVo2);
            list.add(totalVo3);
            list.add(totalVo4);
            list.add(totalVo5);
            result.setMessage("统计成功");
            result.setSuccess(true);
            result.setData(list);
            log.info("新房总数", total);
        }

        return result;
    }


    @ApiOperation("根据本推荐查询房源")
    @PostMapping("/api/house/isRecommend")
    public Result isRecommend(@RequestBody IsRecd isRecd) throws Exception {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(isRecd)) {
            result.setMessage("查询入参错误");
            result.setSuccess(false);
            return result;
        }
        Page page = PageHelper.startPage(isRecd.getPageIndex(), isRecd.getPageSize());
        List<House> houseList = houseService.selectByIsRecommend(isRecd.getIsRecommend());
        PageInfo info = new PageInfo<>(page.getResult());
        if (!StringUtils.isEmpty(houseList) && houseList.size() > 0) {
            List list = new ArrayList();
            for (House house1 : houseList) {
                ReturnHouse house3 = new ReturnHouse();
                Integer record = historyService.selectByRecordCount(house1.getId());
                if (!StringUtils.isEmpty(record)) {
                    house3.setRecord(record);
                } else {
                    house3.setRecord(0);
                }
                if (isRecd.getPhone().length() == 11) {
                    Collect collect1 = new Collect();
                    collect1.setPhone(isRecd.getPhone());
                    collect1.setHouseId(house1.getId());
                    Collect collect = collectService.selectByCollectHouse(collect1);
                    if (!StringUtils.isEmpty(collect)) {
                        house3.setIsFollow(collect.getState());
                    } else {
                        house3.setIsFollow(0);
                    }
                } else {
                    house3.setIsFollow(0);
                }
                house3.setIsRecommend(house1.getIsRecommend());
                house3.setActualFloor(house1.getActualFloor());
                house3.setAddress(house1.getAddress());
                house3.setApart(house1.getApart());
                house3.setArea(house1.getArea());
                house3.setCity(house1.getCity());
                house3.setContact(house1.getContact());
                house3.setFloorArea(house1.getFloorArea());
                if (!StringUtils.isEmpty(house1.getHandTime())) {
                    house3.setHandTime(dateUtils.dateToString(house1.getHandTime()));
                }
                house3.setHouseName(house1.getHouseName());
                house3.setIsBus(house1.getIsBus());
                house3.setIsFree(house1.getIsFree());
                house3.setIsHome(house1.getIsHome());
                house3.setDeploy(house1.getDeploy());
                house3.setIsMinprice(house1.getIsMinprice());
                house3.setIsMintotalprice(house1.getIsMintotalprice());
                house3.setIsPark(house1.getIsPark());
                house3.setIsSubway(house1.getIsSubway());
                house3.setMinfloorArea(house1.getMinFloorarea());
                house3.setMaxfloorArea(house1.getMaxFloorarea());
                house3.setLeaseWay(house1.getLeaseWay());
                house3.setMainFloor(house1.getMainFloor());
                if (!StringUtils.isEmpty(house1.getOpenTime())) {
                    house3.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
                }
                house3.setQuality(house1.getQuality());
                house3.setPayWay(house1.getPayWay());
                house3.setPhone(house1.getPhone());
                String str3 = house1.getPicUrl();
                String[] arr2 = new String[0];
                str3 = str3.replaceAll("\\[", "");
                str3 = str3.replaceAll("\\]", "");
                str3 = str3.replaceAll("\\\"", "");
                log.info(str3);
                if (str3.length() > 0) {
                    String str4 = str3.substring(0, str3.length());
                    log.info(str4);
                    String[] arr = str4.split(",");
                    house3.setPicUrl(arr);
                } else {
                    house3.setPicUrl(arr2);
                }
                house3.setProvince(house1.getProvince());
                house3.setRemark(house1.getRemark());
                house3.setRenovation(house1.getRenovation());
                house3.setSeeHouse(house1.getSeeHouse());
                house3.setState(house1.getState());
                house3.setTags(house1.getTags());
                house3.setQuality(house1.getQuality());
                house3.setFloors(house1.getFloors());
                house3.setTitle(house1.getTitle());
                house3.setTotalPrice(house1.getTotalPrice());
                house3.setType(house1.getType());
                house3.setHouseYear(house1.getHouseYear());
                house3.setId(house1.getId());
                List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house1.getId());
                List list1 = new ArrayList();
                if (list1 != null) {
                    for (MainApart apart : mainApartList) {
                        MainApartVO mainApartVO = new MainApartVO();
                        mainApartVO.setApart(apart.getApart());
                        mainApartVO.setMainArea(apart.getMainArea());
                        mainApartVO.setPicUrl(apart.getPicUrl());
                        mainApartVO.setState(apart.getState());
                        list1.add(mainApartVO);
                    }
                }
                house3.setMainApart(list1);
                List<RHouse> rHouseList1 = houseService.selectByDistinct(house1.getId());
                if (!StringUtils.isEmpty(rHouseList1)) {
                    List list2 = new ArrayList();
                    for (RHouse rHouse1 : rHouseList1) {
                        Business business = new Business();
                        business.setId(rHouse1.getBusId());
                        Business business1 = businessService.selectByPrimaryKey(business);
                        house3.setBusiness(business1);
                    }

                    List<RHouse> rHouseList = houseService.selectByList(house1.getId());

                    if (rHouseList != null) {
                        List list3 = new ArrayList();
                        for (RHouse rHouse1 : rHouseList) {
                            Agent agent = agentService.selectByAgentId(rHouse1.getAgentId());
                            if (StringUtils.isEmpty(agent))
                                continue;
                            list3.add(agent);
                        }
                        house3.setAgent(list3);
                    }
                }
                list.add(house3);
            }
            Map map = new HashMap();
            map.put("rows", list);
            map.put("pageIndex", isRecd.getPageIndex());
            map.put("pageSize", isRecd.getPageSize());
            map.put("totalCount", info.getTotal());
            result.setSuccess(true);
            result.setMessage("推荐房源的查询成功");
            result.setData(map);
        } else {
            isRecd.setIsRecommend(null);
            Page page1 = PageHelper.startPage(isRecd.getPageIndex(), isRecd.getPageSize());
            List<House> houseList1 = houseService.selectByIsRecommend(isRecd.getIsRecommend());
            PageInfo info1 = new PageInfo<>(page1.getResult());
            List list4 = new ArrayList();
            for (House house2 : houseList1) {

                ReturnHouse house3 = new ReturnHouse();
                Integer record = historyService.selectByRecordCount(house2.getId());
                if (!StringUtils.isEmpty(record)) {
                    house3.setRecord(record);
                } else {
                    house3.setRecord(0);
                }
                if (isRecd.getPhone().length() == 11) {
                    Collect collect1 = new Collect();
                    collect1.setPhone(isRecd.getPhone());
                    collect1.setHouseId(house2.getId());
                    Collect collect = collectService.selectByCollectHouse(collect1);
                    if (!StringUtils.isEmpty(collect)) {
                        house3.setIsFollow(collect.getState());
                    } else {
                        house3.setIsFollow(0);
                    }
                } else {
                    house3.setIsFollow(0);
                }

                house3.setIsRecommend(house2.getIsRecommend());
                house3.setActualFloor(house2.getActualFloor());
                house3.setAddress(house2.getAddress());
                house3.setApart(house2.getApart());
                house3.setArea(house2.getArea());
                house3.setCity(house2.getCity());
                house3.setContact(house2.getContact());
                house3.setFloorArea(house2.getFloorArea());
                if (!StringUtils.isEmpty(house2.getHandTime())) {
                    house3.setHandTime(dateUtils.dateToString(house2.getHandTime()));
                }
                house3.setHouseName(house2.getHouseName());
                house3.setIsBus(house2.getIsBus());
                house3.setIsFree(house2.getIsFree());
                house3.setIsHome(house2.getIsHome());
                house3.setDeploy(house2.getDeploy());
                house3.setIsMinprice(house2.getIsMinprice());
                house3.setIsMintotalprice(house2.getIsMintotalprice());
                house3.setIsPark(house2.getIsPark());
                house3.setIsSubway(house2.getIsSubway());
                house3.setMinfloorArea(house2.getMinFloorarea());
                house3.setMaxfloorArea(house2.getMaxFloorarea());
                house3.setLeaseWay(house2.getLeaseWay());
                house3.setMainFloor(house2.getMainFloor());
                if (!StringUtils.isEmpty(house2.getOpenTime())) {
                    house3.setOpenTime(dateUtils.dateToString(house2.getOpenTime()));
                }
                house3.setQuality(house2.getQuality());
                house3.setPayWay(house2.getPayWay());
                house3.setPhone(house2.getPhone());
                String str3 = house2.getPicUrl();
                String[] arr2 = new String[0];
                str3 = str3.replaceAll("\\[", "");
                str3 = str3.replaceAll("\\]", "");
                str3 = str3.replaceAll("\\\"", "");
                log.info(str3);
                if (str3.length() > 0) {
                    String str4 = str3.substring(0, str3.length());
                    log.info(str4);
                    String[] arr = str4.split(",");
                    house3.setPicUrl(arr);
                } else {
                    house3.setPicUrl(arr2);
                }
                house3.setProvince(house2.getProvince());
                house3.setRemark(house2.getRemark());
                house3.setRenovation(house2.getRenovation());
                house3.setSeeHouse(house2.getSeeHouse());
                house3.setState(house2.getState());
                house3.setTags(house2.getTags());
                house3.setQuality(house2.getQuality());
                house3.setFloors(house2.getFloors());
                house3.setTitle(house2.getTitle());
                house3.setTotalPrice(house2.getTotalPrice());
                house3.setType(house2.getType());
                house3.setHouseYear(house2.getHouseYear());
                house3.setId(house2.getId());
                List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house2.getId());
                List list1 = new ArrayList();
                if (list1 != null) {
                    for (MainApart apart : mainApartList) {
                        MainApartVO mainApartVO = new MainApartVO();
                        mainApartVO.setApart(apart.getApart());
                        mainApartVO.setMainArea(apart.getMainArea());
                        mainApartVO.setPicUrl(apart.getPicUrl());
                        mainApartVO.setState(apart.getState());
                        list1.add(mainApartVO);
                    }
                }
                house3.setMainApart(list1);
                List<RHouse> rHouseList1 = houseService.selectByDistinct(house2.getId());
                if (!StringUtils.isEmpty(rHouseList1)) {

                    for (RHouse rHouse1 : rHouseList1) {
                        Business business = new Business();
                        business.setId(rHouse1.getBusId());
                        Business business1 = businessService.selectByPrimaryKey(business);
                        house3.setBusiness(business1);
                    }


                    List<RHouse> rHouseList = houseService.selectByList(house2.getId());
                    if (rHouseList != null) {
                        List list3 = new ArrayList();
                        for (RHouse rHouse1 : rHouseList) {
                            Agent agent = agentService.selectByAgentId(rHouse1.getAgentId());
                            list3.add(agent);
                        }
                        house3.setAgent(list3);
                        result.setData(house3);
                        result.setMessage("chaxunchenggong");
                        result.setSuccess(true);
                    } else {
                        result.setData(house3);
                        result.setMessage("chaxunchenggong");
                        result.setSuccess(true);
                    }
                }

                list4.add(house3);
            }
            Map map = new HashMap();
            map.put("rows", list4);
            map.put("pageIndex", isRecd.getPageIndex());
            map.put("pageSize", isRecd.getPageSize());
            map.put("totalCount", info1.getTotal());
            result.setSuccess(true);
            result.setMessage("没有推荐房源的查询成功");
            result.setData(map);
        }

        return result;
    }


    @ApiOperation("查找该商铺下的所有房源")
    @PostMapping("/api/business/house/select")
    public Result house(@RequestBody BusIdVO2 busIdVO) {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(busIdVO.getBusId())) {
            result.setMessage("入参错误");
            result.setSuccess(false);
        } else {
            List list = new ArrayList();
            Page page = PageHelper.startPage(busIdVO.getPageIndex(), busIdVO.getPageSize());
            List<ReturnHouse> returnHouses = houseService.queryByDescTest(busIdVO.getBusId(), busIdVO.getType());
            PageInfo info = new PageInfo<>(page.getResult());
            if (returnHouses.size() > 0) {
                for (ReturnHouse returnHouse : returnHouses) {
                    House house1 = houseService.selectByHouseId(returnHouse.getId());
                    ReturnHouse house3 = new ReturnHouse();
                    Integer record = historyService.selectByRecordCount(house1.getId());
                    house3.setRecord(record);
                    house3.setCreateTime(house1.getCreateTime());
                    house3.setIsRecommend(house1.getIsRecommend());
                    house3.setActualFloor(house1.getActualFloor());
                    house3.setAddress(house1.getAddress());
                    house3.setApart(house1.getApart());
                    house3.setArea(house1.getArea());
                    house3.setCity(house1.getCity());
                    house3.setDirection(house1.getDirection());
                    house3.setContact(house1.getContact());
                    house3.setFloorArea(house1.getFloorArea());
                    if (!StringUtils.isEmpty(house1.getHandTime())) {
                        house3.setHandTime(dateUtils.dateToString(house1.getHandTime()));
                    }
                    house3.setHouseName(house1.getHouseName());
                    house3.setIsBus(house1.getIsBus());
                    house3.setIsFree(house1.getIsFree());
                    house3.setIsHome(house1.getIsHome());
                    house3.setDeploy(house1.getDeploy());
                    house3.setIsMinprice(house1.getIsMinprice());
                    house3.setIsMintotalprice(house1.getIsMintotalprice());
                    house3.setIsPark(house1.getIsPark());
                    house3.setIsSubway(house1.getIsSubway());
                    house3.setMinfloorArea(house1.getMinFloorarea());
                    house3.setMaxfloorArea(house1.getMaxFloorarea());
                    house3.setLeaseWay(house1.getLeaseWay());
                    house3.setMainFloor(house1.getMainFloor());
                    if (!StringUtils.isEmpty(house1.getOpenTime())) {
                        house3.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
                    }
                    house3.setQuality(house1.getQuality());
                    house3.setPayWay(house1.getPayWay());
                    house3.setPhone(house1.getPhone());
                    String str3 = house1.getPicUrl();
                    String[] arr2 = new String[0];
                    str3 = str3.replaceAll("\\[", "");
                    str3 = str3.replaceAll("\\]", "");
                    str3 = str3.replaceAll("\\\"", "");
                    log.info("图片路径转换{}", str3);
                    if (str3.length() > 0) {
                        String str4 = str3.substring(0, str3.length());
                        log.info(str4);
                        String[] arr = str4.split(",");
                        house3.setPicUrl(arr);
                    } else {
                        house3.setPicUrl(arr2);
                    }
                    house3.setProvince(house1.getProvince());
                    house3.setRemark(house1.getRemark());
                    house3.setRenovation(house1.getRenovation());
                    house3.setSeeHouse(house1.getSeeHouse());
                    house3.setState(house1.getState());
                    house3.setTags(house1.getTags());
                    house3.setQuality(house1.getQuality());
                    house3.setFloors(house1.getFloors());
                    house3.setTitle(house1.getTitle());
                    house3.setTotalPrice(house1.getTotalPrice());
                    house3.setType(house1.getType());
                    house3.setPrice(house1.getPrice());
                    house3.setHouseYear(house1.getHouseYear());
                    house3.setId(house1.getId());
                    List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house1.getId());
                    List list2 = new ArrayList();
                    if (mainApartList != null && mainApartList.size() > 0) {
                        for (MainApart apart : mainApartList) {
                            MainApartVO mainApartVO = new MainApartVO();
                            mainApartVO.setApart(apart.getApart());
                            mainApartVO.setMainArea(apart.getMainArea());
                            mainApartVO.setPicUrl(apart.getPicUrl());
                            mainApartVO.setState(apart.getState());
                            list2.add(mainApartVO);
                        }
                    }
                    house3.setMainApart(list2);
                    List<RHouse> rHouseList1 = houseService.selectByDistinct(house1.getId());
                    if (!StringUtils.isEmpty(rHouseList1) && rHouseList1.size() > 0) {
                        for (RHouse rHouse2 : rHouseList1) {
                            Business business = new Business();
                            business.setId(rHouse2.getBusId());
                            Business business1 = businessService.selectByPrimaryKey(business);
                            if (StringUtils.isEmpty(business1))
                                continue;
                            house3.setBusiness(business1);
                        }

                    }
                    List<RHouse> rHouseList = houseService.selectByList(house1.getId());
                    List list3 = new ArrayList();
                    if (rHouseList != null && rHouseList.size() > 0) {
                        for (RHouse rHouse3 : rHouseList) {
                            Agent agent = agentService.selectByAgentId(rHouse3.getAgentId());
                            if (StringUtils.isEmpty(agent))
                                continue;
                            list3.add(agent);
                        }
                        house3.setAgent(list3);
                    }
                    list.add(house3);

                }
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", busIdVO.getPageIndex());
                map.put("pageSize", busIdVO.getPageSize());
                result.setSuccess(true);
                result.setMessage("查询成功");
                result.setData(map);
            } else {
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", busIdVO.getPageIndex());
                map.put("pageSize", busIdVO.getPageSize());
                result.setSuccess(true);
                result.setMessage("查询成功,暂无数据");
                result.setData(map);

            }

        }
        return result;

    }


    @Data
    public static class BusIdVO2 {
        @ApiModelProperty(value = "商户编号", required = true, example = "1")
        private Integer busId;
        @ApiModelProperty(example = "1")
        private Integer pageIndex;
        @ApiModelProperty(example = "2")
        private Integer pageSize;
        @ApiModelProperty(value = "类型")
        private Integer type;
    }

    @ApiModel
    @Data
    public static class ID {
        @ApiModelProperty(example = "1")
        private Integer busId;
    }

    @ApiModel
    @Data
    public static class DelHouseId {
        @ApiModelProperty(value = "房源编码", required = true, example = "1")
        private Integer id;
    }

    @ApiModel
    @Data
    public static class HouseId {
        @ApiModelProperty(value = "房源编码", example = "1")
        private Integer id;
    }


    @Data
    public static class IsRecd {
        private Integer isRecommend;
        private String phone;
        private Integer pageSize;
        private Integer pageIndex;
    }

    @Data
    @ApiModel
    public static class HUVO {
        @ApiModelProperty(value = "用户手机号", required = true, example = "13100000001")
        private String phone;
        @ApiModelProperty(value = "房源编码", required = true, example = "1")
        private Integer id;
    }


}
