package com.w.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w.entity.*;
import com.w.service.*;
import com.w.utils.DateUtils;
import com.w.utils.Result;
import com.w.vo.MainApartVO;
import com.w.vo.ReturnHistoryHouseVo;
import com.w.vo.ReturnHouse;
import com.w.vo.ReturnUserVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "HistoryController", tags = "浏览历史记录")
@RestController
public class HistoryController {
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


    //必传参数openID(查询所有的历史记录)
    @ApiOperation("查询该用户下所有的历史记录")
    @PostMapping("/api/history/select")
    public Result select(@RequestBody HistoryVo historyVo) throws Exception {
        Result result = new Result();

        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(historyVo.getPhone())) {
            result.setMessage("手机号为空");
            result.setSuccess(false);
        } else {
            User user = new User();
            user.setPhone(historyVo.getPhone());
            User user1 = userService.selectByPhoneNo(user);
            if (user1 != null) {
                History history = new History();
                if (!StringUtils.isEmpty(user1.getOpenId())) {
                    List list = new ArrayList();
                    history.setOpenId(user1.getOpenId());

                    Page page = PageHelper.startPage(historyVo.getPageIndex(), historyVo.getPageSize());
                    List<History> houses = historyService.selectByOpenId(history);
                    PageInfo info = new PageInfo<>(page.getResult());
                    if (!StringUtils.isEmpty(houses)) {
                        for (History history1 : houses) {
                            List<RHouse> rHouseList = houseService.selectByList(history.getHouseId());
                            List list1 = new ArrayList();
                            if (rHouseList != null) {
                                for (RHouse rHouse : rHouseList) {
                                    Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                                    if (StringUtils.isEmpty(agent))
                                        continue;
                                    list1.add(agent);
                                }
                            }
                            House house1 = houseService.selectByHouseId(history1.getHouseId());
                            if (!StringUtils.isEmpty(house1)) {
                                ReturnHistoryHouseVo house3 = new ReturnHistoryHouseVo();
                                Collect collect1 = new Collect();
                                collect1.setPhone(user1.getPhone());
                                collect1.setHouseId(house1.getId());
                                Collect collect = collectService.selectByCollectHouse(collect1);
                                if (!StringUtils.isEmpty(collect)) {
                                    house3.setIsFollow(collect.getState());
                                } else {
                                    house3.setIsFollow(0);
                                }

                                Integer record = historyService.selectByRecordCount(house1.getId());
                                if (!StringUtils.isEmpty(record)) {
                                    house3.setRecord(record);
                                } else {
                                    house3.setRecord(0);
                                }
                                List<RHouse> rHouseList2 = houseService.selectByDistinct(house1.getId());
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
                                house3.setDirection(house1.getDirection());
                                house3.setBrowseTime(history1.getCreateTime());
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
                                house3.setIsMinprice(house1.getIsMinprice());
                                house3.setIsMintotalprice(house1.getIsMintotalprice());
                                house3.setIsPark(house1.getIsPark());
                                house3.setIsSubway(house1.getIsSubway());
                                house3.setLeaseWay(house1.getLeaseWay());
                                List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house1.getId());
                                List list2 = new ArrayList();
                                if (list2 != null && list2.size() > 0) {
                                    for (MainApart apart : mainApartList) {
                                        MainApartVO mainApartVO = new MainApartVO();
                                        mainApartVO.setApart(apart.getApart());
                                        mainApartVO.setMainArea(apart.getMainArea());
                                        mainApartVO.setPicUrl(apart.getPicUrl());

                                        list2.add(mainApartVO);
                                    }
                                }

                                house3.setMainApart(list2);
                                house3.setMainFloor(house1.getMainFloor());
                                if (!StringUtils.isEmpty(house1.getOpenTime())) {
                                    house3.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
                                }
                                house3.setQuality(house1.getQuality());
                                house3.setPayWay(house1.getPayWay());
                                house3.setDeploy(house1.getDeploy());
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
                                if (!StringUtils.isEmpty(house1.getHandTime())) {
                                    house3.setHandTime(dateUtils.dateToString(house1.getHandTime()));
                                }
                                if (!StringUtils.isEmpty(house1.getOpenTime())) {
                                    house3.setOpenTime(dateUtils.dateToString(house1.getOpenTime()));
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
                                house3.setMaxfloorArea(house1.getMaxFloorarea());
                                house3.setMinfloorArea(house1.getMinFloorarea());
                                house3.setId(house1.getId());
                                house3.setAgent(list1);
                                list.add(house3);
                            }
                            Map querymap = new HashMap();
                            querymap.put("rows", list);
                            querymap.put("totalCount", info.getTotal());
                            result.setMessage("查询成功");
                            result.setSuccess(true);
                            result.setData(querymap);
                        }
                    } else {
                        Map querymap = new HashMap();
                        querymap.put("rows", list);
                        querymap.put("totalCount", info.getTotal());
                        result.setMessage("查询成功");
                        result.setSuccess(true);
                        result.setData(querymap);
                    }
                } else {
                    result.setMessage("用户相关数据为空");
                    result.setSuccess(false);
                }
            } else {
                result.setMessage("暂无用户信息");
                result.setSuccess(false);
            }
        }
        return result;
    }

    @ApiOperation("查询该商户下的所有浏览用户信息")
    @PostMapping("/api/history/browse")
    public Result browse(@RequestBody BusinessVO businessVO) {
        Result result = new Result();
        if (!StringUtils.isEmpty(businessVO.getBusId())) {
            Page page = PageHelper.startPage(businessVO.getPageIndex(), businessVO.getPageSize());
            List<String> historyList = historyService.selectByUserList(businessVO.getBusId());
            PageInfo info = new PageInfo<>(page.getResult());
            List list = new ArrayList();
            if (!StringUtils.isEmpty(historyList)) {
                for (String phone : historyList) {
                    if (phone.equals("200")) {
                        continue;
                    }
                    ReturnUserVO userVO = new ReturnUserVO();
                    userVO.setPhone(phone);
                    History history1 = new History();
                    Collect collect = new Collect();
                    history1.setBusId(businessVO.getBusId());
                    history1.setPhone(phone);
                    collect.setPhone(phone);
                    collect.setBusId(businessVO.getBusId());
                    Integer recode1 = historyService.selectByUserPhoneAndBusId(businessVO.getBusId(), history1.getPhone());
                    if (!StringUtils.isEmpty(recode1)) {
                        userVO.setRecord(recode1);
                    } else {
                        userVO.setRecord(0);
                    }

                    Integer collectCount = collectService.selectByUserCount(collect);
                    if (StringUtils.isEmpty(collectCount)) {
                        userVO.setFollow(0);
                    } else {
                        userVO.setFollow(collectCount);
                    }
                    list.add(userVO);
                }
                Map map = new HashMap();
                map.put("rows", list);
                map.put("pageSize", businessVO.getPageSize());
                map.put("pageIndex", businessVO.getPageIndex());
                map.put("totalCount", info.getTotal());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);
            } else {
                Map map = new HashMap();
                map.put("rows", list);
                map.put("pageSize", businessVO.getPageSize());
                map.put("pageIndex", businessVO.getPageIndex());
                map.put("totalCount", info.getTotal());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);
            }
        } else {
            result.setSuccess(false);
            result.setMessage("查询浏览用户信息失败");
        }
        return result;
    }

    @ApiOperation("查询该商户下的用户浏览房源信息")
    @PostMapping("/api/history/house")
    public Result house(@RequestBody BrowseHouseVO browseHouseVO) throws Exception {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(browseHouseVO.getBusId()) || StringUtils.isEmpty(browseHouseVO.getPhone())) {
            result.setSuccess(false);
            result.setMessage("商家权限不足或该用户未授权");
        } else {
            History history = new History();
            history.setPhone(browseHouseVO.getPhone());
            history.setBusId(browseHouseVO.getBusId());
            Page page = PageHelper.startPage(browseHouseVO.getPageIndex(), browseHouseVO.getPageSize());
            List<History> historyList = historyService.selectByPhoneAndBusId(history);
            PageInfo info = new PageInfo(page.getResult());
            List list = new ArrayList();
            if (StringUtils.isEmpty(historyList)) {
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", browseHouseVO.getPageIndex());
                map.put("pageSize", browseHouseVO.getPageSize());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);
            } else {
                for (History history1 : historyList) {
                    ReturnHouse house3 = new ReturnHouse();
                    House house = houseService.selectByHouseId(history1.getHouseId());
                    if (StringUtils.isEmpty(house)) {
                        continue;
                    }
                    if (history1.getPhone() != null && history1.getPhone().length() == 11) {
                        Collect collect1 = new Collect();
                        collect1.setPhone(history1.getPhone());
                        collect1.setHouseId(house.getId());
                        Collect collect = collectService.selectByCollectHouse(collect1);
                        if (!StringUtils.isEmpty(collect)) {
                            house3.setIsFollow(collect.getState());
                        } else {
                            house3.setIsFollow(0);
                        }
                    }
                    History history2 = historyService.selectByDetailCount(house.getId(), history1.getPhone());
                    if (!StringUtils.isEmpty(history2.getBrowseCount())) {
                        house3.setRecord(history2.getBrowseCount());
                    } else {
                        house3.setRecord(0);
                    }
                    house3.setUserPhone(history1.getPhone());
                    house3.setCreateTime(history1.getCreateTime());
                    house3.setActualFloor(house.getActualFloor());
                    house3.setAddress(house.getAddress());
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
                    List<RHouse> rHouseList = houseService.selectByList(house.getId());
                    List list1 = new ArrayList();
                    if (rHouseList != null) {
                        for (RHouse rHouse : rHouseList) {
                            Agent agent = agentService.selectByAgentId(rHouse.getAgentId());
                            if (StringUtils.isEmpty(agent)) {
                                continue;
                            }
                            list1.add(agent);
                        }
                        house3.setAgent(list1);
                    }
                    list.add(house3);

                }
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", browseHouseVO.getPageIndex());
                map.put("pageSize", browseHouseVO.getPageSize());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);
            }
        }

        return result;
    }

    @Data
    @ApiModel
    public static class HistoryVo {
        @ApiModelProperty(value = "用户手机号", required = true)
        private String phone;
        @ApiModelProperty(value = "页码", required = true)
        private Integer pageIndex;
        @ApiModelProperty(value = "条数", required = true)
        private Integer pageSize;
    }

    @Data
    @ApiModel
    public static class BusinessVO {
        private Integer busId;
        private Integer pageSize;
        private Integer pageIndex;
    }

    @Data
    @ApiModel
    public static class BrowseHouseVO {
        private Integer busId;
        private String phone;
        private Integer pageSize;
        private Integer pageIndex;
    }

}
