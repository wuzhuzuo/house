package com.w.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w.entity.*;
import com.w.service.*;
import com.w.utils.DateUtils;
import com.w.utils.Result;
import com.w.vo.MainApartVO;
import com.w.vo.ReturnHouse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wu
 * @Date :create in 9:13 2020/6/22
 */
@Slf4j
@Api(value = "IndexController", tags = "首页数据模块")
@RestController
public class IndexController {
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


    @ApiOperation("首页相关数据")
    @PostMapping("/api/index/select")
    public Result itemTest(@RequestBody IndexVo indexVo) {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();

        Page page = PageHelper.startPage(indexVo.getPageIndex(), indexVo.getPageSize());
        List<ReturnHouse> returnHouses = houseService.findByCountType1(indexVo.getCity());
        PageInfo info = new PageInfo<>(page.getResult());
        ReturnIndexDTO returnIndexDTO1 = new ReturnIndexDTO();
        if (returnHouses.size() > 0) {
            List<ReturnHouse> list11 = new ArrayList<>();
            for (ReturnHouse returnHouse : returnHouses) {
                House house = new House();
                house.setId(returnHouse.getId());
                House house1 = houseService.selectByPrimaryKey(house);
                ReturnHouse house3 = new ReturnHouse();
                house3.setRecord(returnHouse.getRecord());
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
                }

                list11.add(house3);
            }

            returnIndexDTO1.setTitle("新房数据");
            returnIndexDTO1.setRows(list11);

        } else {
            List list = new ArrayList();
            returnIndexDTO1.setTitle("新房数据");
            returnIndexDTO1.setRows(list);
        }
        Page page1 = PageHelper.startPage(indexVo.getPageIndex(), indexVo.getPageSize());
        List<ReturnHouse> returnHouses1 = houseService.findByCountType2(indexVo.getCity());
        PageInfo info1 = new PageInfo<>(page1.getResult());
        ReturnIndexDTO returnIndexDTO2 = new ReturnIndexDTO();
        if (returnHouses1.size() > 0) {
            List<ReturnHouse> list12 = new ArrayList<>();
            for (ReturnHouse returnHouse : returnHouses1) {
                House house = new House();
                house.setId(returnHouse.getId());
                House house1 = houseService.selectByPrimaryKey(house);
                ReturnHouse house3 = new ReturnHouse();
                house3.setRecord(returnHouse.getRecord());
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
                }

                list12.add(house3);
            }

            returnIndexDTO2.setTitle("二手房数据");
            returnIndexDTO2.setRows(list12);

        } else {
            List list = new ArrayList();
            returnIndexDTO2.setTitle("二手房数据");
            returnIndexDTO2.setRows(list);
        }
        Page page2 = PageHelper.startPage(indexVo.getPageIndex(), indexVo.getPageSize());
        List<ReturnHouse> returnHouses2 = houseService.findByCountType3(indexVo.getCity());
        PageInfo info2 = new PageInfo<>(page2.getResult());
        ReturnIndexDTO returnIndexDTO3 = new ReturnIndexDTO();
        if (returnHouses2.size() > 0) {
            List<ReturnHouse> list13 = new ArrayList<>();
            for (ReturnHouse returnHouse : returnHouses2) {
                House house = new House();
                house.setId(returnHouse.getId());
                House house1 = houseService.selectByPrimaryKey(house);
                ReturnHouse house3 = new ReturnHouse();
                house3.setRecord(returnHouse.getRecord());
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
                }

                list13.add(house3);
            }
            returnIndexDTO3.setTitle("租房数据");
            returnIndexDTO3.setRows(list13);
        } else {
            List list = new ArrayList();
            returnIndexDTO3.setTitle("租房数据");
            returnIndexDTO3.setRows(list);
        }

        List list = new ArrayList();
        list.add(returnIndexDTO3);
        list.add(returnIndexDTO1);
        list.add(returnIndexDTO2);
        result.setData(list);
        return result;
    }

    @Data
    static class IndexVo {
        private Integer pageSize;
        private Integer pageIndex;
        private String city;
    }

    @Data
    static class ReturnIndexDTO {
        private String title;
        private List rows;
    }
}
