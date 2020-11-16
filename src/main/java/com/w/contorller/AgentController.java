package com.w.contorller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.w.entity.*;
import com.w.service.*;
import com.w.utils.DateUtils;
import com.w.utils.Result;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Api(value = "AgentController", tags = "经纪人模块")
@RestController
public class AgentController {
    @Autowired
    private AgentService agentService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private AccountService accountService;


    @ApiOperation("添加经纪人")
    @PostMapping("/api/agent/insert")
    public Result insert(@RequestBody AgentVO agentVO) throws ParseException {
        Result result = new Result();
        Agent agent = new Agent();
        Account account = new Account();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isEmpty(agentVO.getBusId())) {
            result.setMessage("暂无商家编码");
            result.setSuccess(false);
        } else {
            agent.setBusId(agentVO.getBusId());
            agent.setAgentName(agentVO.getAgentName());
            agent.setPicUrl(agentVO.getPicUrl());
            agent.setPhone(agentVO.getPhone());
            agent.setRemark(agentVO.getRemark());
            agent.setCreateTime(df.parse(df.format(new Date())));
            account.setType(2);
            account.setUserName(agentVO.getPhone());
            Account account1 = accountService.selectByUserName(account);
            Agent agent2 = agentService.selectByPhone(agentVO.getPhone());

            if (!StringUtils.isEmpty(agent2) || !StringUtils.isEmpty(account1)) {
                result.setSuccess(false);
                result.setMessage("该经纪人手机号已被注册,请联系管理员");
                return result;
            } else {

                agentService.insertSelective(agent);
                Agent agent1 = agentService.selectByPrimaryKey(agent);
                account.setPassWord("123456");
                account.setUserName(agent1.getPhone());
                account.setAgentId(agent1.getId());
                account.setBusId(agent1.getBusId());
                account.setType(2);
                if (account1 == null) {
                    accountService.insertSelective(account);
                    Account account2 = accountService.selectByUserNameAndPassword(account);
                    result.setMessage("添加成功");
                    result.setSuccess(true);
                    result.setData(account2);
                }
                result.setSuccess(true);
                result.setMessage("信息录入成功");
                result.setData(agent1);
            }
        }
        return result;
    }

    @ApiOperation("查找该经纪人下的所有房源")
    //根据经纪人code查找该经纪人下的所有房源
    @PostMapping("/api/agent/house/select")
    public Result houseSelect(@RequestBody AgentVO2 agentVO2) {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(agentVO2.getAgentId())) {
            result.setMessage("入参错误");
            result.setSuccess(false);
        } else {
            List list = new ArrayList();
            Page page = PageHelper.startPage(agentVO2.getPageIndex(), agentVO2.getPageSize());
            List<RHouse> rHouses = agentService.selectByAgentAndHouse(agentVO2.getAgentId(), agentVO2.getType());
            PageInfo info = new PageInfo<>(page.getResult());
            if (StringUtils.isEmpty(rHouses) && rHouses.size() < 0) {
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", agentVO2.getPageIndex());
                map.put("pageSize", agentVO2.getPageSize());
                result.setSuccess(true);
                result.setMessage("查询成功");
                result.setData(map);
            } else {
                for (RHouse rHouse1 : rHouses) {
                    House house = houseService.selectByHouseId(rHouse1.getHouseId());
                    if (StringUtils.isEmpty(house))
                        continue;
                    ReturnHouse house3 = new ReturnHouse();
                    Integer record = historyService.selectByRecordCount(house.getId());
                    if (!StringUtils.isEmpty(record)) {
                        house3.setRecord(record);
                    } else {
                        house3.setRecord(0);
                    }
                    house3.setIsRecommend(house.getIsRecommend());
                    house3.setActualFloor(house.getActualFloor());
                    house3.setAddress(house.getAddress());
                    house3.setApart(house.getApart());
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
                    house3.setDeploy(house.getDeploy());
                    house3.setIsMinprice(house.getIsMinprice());
                    house3.setIsMintotalprice(house.getIsMintotalprice());
                    house3.setIsPark(house.getIsPark());
                    house3.setIsSubway(house.getIsSubway());
                    house3.setMinfloorArea(house.getMinFloorarea());
                    house3.setMaxfloorArea(house.getMaxFloorarea());
                    house3.setLeaseWay(house.getLeaseWay());
                    house3.setMainFloor(house.getMainFloor());
                    if (!StringUtils.isEmpty(house.getOpenTime())) {
                        house3.setOpenTime(dateUtils.dateToString(house.getOpenTime()));
                    }
                    house3.setQuality(house.getQuality());
                    house3.setPayWay(house.getPayWay());
                    house3.setPhone(house.getPhone());
                    String str3 = house.getPicUrl();
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
                    house3.setProvince(house.getProvince());
                    house3.setRemark(house.getRemark());
                    house3.setRenovation(house.getRenovation());
                    house3.setSeeHouse(house.getSeeHouse());
                    house3.setState(house.getState());
                    house3.setTags(house.getTags());
                    house3.setQuality(house.getQuality());
                    house3.setFloors(house.getFloors());
                    house3.setTitle(house.getTitle());
                    house3.setTotalPrice(house.getTotalPrice());
                    house3.setType(house.getType());
                    house3.setHouseYear(house.getHouseYear());
                    house3.setId(house.getId());
                    List<MainApart> mainApartList = houseService.selectByMainApartAndhouseId(house.getId());
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
                    List<RHouse> rHouseList1 = houseService.selectByDistinct(house.getId());
                    if (!StringUtils.isEmpty(rHouseList1)) {

                        for (RHouse rHouse2 : rHouseList1) {
                            Business business = new Business();
                            business.setId(rHouse2.getBusId());
                            Business business1 = businessService.selectByPrimaryKey(business);
                            house3.setBusiness(business1);
                        }
                        List<RHouse> rHouseList = houseService.selectByList(house.getId());
                        if (rHouseList != null) {
                            List list3 = new ArrayList();
                            for (RHouse rHouse3 : rHouseList) {
                                Agent agent = agentService.selectByAgentId(rHouse3.getAgentId());
                                if (StringUtils.isEmpty(agent))
                                    continue;
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
                    list.add(house3);

                }
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", agentVO2.getPageIndex());
                map.put("pageSize", agentVO2.getPageSize());
                result.setSuccess(true);
                result.setMessage("查询成功");
                result.setData(map);
            }

        }
        log.info(result.toString());
        return result;
    }

    @ApiOperation("修改经纪人信息")
    @PostMapping("/api/agent/update")
    public Result update(@RequestBody UpdataByAgentVo agentVo) throws ParseException {
        Result result = new Result();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isEmpty(agentVo.getId())) {
            result.setSuccess(false);
            result.setMessage("请输入需要修改的编码");
        } else {
            Agent agent = new Agent();
            agent.setCreateTime(df.parse(df.format(new Date())));
            agent.setId(agentVo.getId());
            agent.setAgentName(agentVo.getAgentName());
            agent.setBusId(agentVo.getBusId());
            agent.setPicUrl(agentVo.getPicUrl());
            agent.setRemark(agentVo.getRemark());
            agent.setState(agentVo.getState());
            agent.setPhone(agentVo.getPhone());
            agentService.updateByPrimaryKeySelective(agent);
            Agent agent1 = agentService.selectByPrimaryKey(agent);
            result.setSuccess(true);
            result.setMessage("修改成功");
            result.setData(agent1);
        }
        return result;
    }

    @ApiOperation("删除经纪人")
    @PostMapping("/api/agent/delete")
    public Result delete(@RequestBody AgentIdVO agent) {
        Result result = new Result();
        if (StringUtils.isEmpty(agent.getId())) {
            result.setSuccess(false);
            result.setMessage("暂无参数");
        } else {
            Agent agent1 = new Agent();
            agent1.setId(agent.getId());
         /*   List<RHouse> rHouseList = houseService.selectByHouse(agent.getId());
            if (!StringUtils.isEmpty(rHouseList)) {
                for (RHouse rHouse : rHouseList) {
                    if (!StringUtils.isEmpty(rHouse.getHouseId()) && !StringUtils.isEmpty(rHouse.getAgentId())) {
                        houseService.deleteByHouseIdAndAgentId(rHouse);
                        log.info("删除经济人所关注的房源成功");
                    }
                }
            }*/
            agentService.deleteByPrimaryKey(agent1);
            result.setSuccess(true);
            result.setMessage("删除成功");
        }
        return result;
    }

    //通过商户编号查询经纪人
    @ApiOperation("查询该商铺下所有经纪人")
    @PostMapping("/api/agent/select")
    public Result queryAll(@RequestBody BusIdVO busIdVO) {
        Result result = new Result();
        Map map = new HashMap();
        if (StringUtils.isEmpty(busIdVO.getBusId())) {
            result.setMessage("请输入商家编码");
            result.setSuccess(false);
        } else {
            Agent agent = new Agent();
            agent.setBusId(busIdVO.getBusId());
            List<Agent> list = agentService.list(agent);
            map.put("rows", list);
            result.setMessage("查询成功");
            result.setSuccess(true);
            result.setData(list);

        }

        return result;
    }

    //统计该经纪人的房源数量
    @ApiOperation("统计该经纪人的房源数量")
    @PostMapping("/api/agent/house/total")
    public Result queryTotal(@RequestBody AgentDto agentDto) {

        log.info("经纪人房源统计入参{}", agentDto);
        Result result = new Result();
        if (StringUtils.isEmpty(agentDto.getAgentId()) || StringUtils.isEmpty(agentDto.getBusId())) {
            result.setSuccess(false);
            result.setMessage("入参出错");
        } else {
            List list = new ArrayList();
            TotalVo totalVo1 = new TotalVo();
            Integer total = agentService.selectByAgentAndHouseType1(agentDto.getBusId(), agentDto.getAgentId());
            log.info("经纪人新房统计{}", total);
            if (StringUtils.isEmpty(total)) {
                totalVo1.setLabel("新房");
                totalVo1.setTotalCount(0);
            } else {
                totalVo1.setLabel("新房");
                totalVo1.setTotalCount(total);
            }

            TotalVo totalVo2 = new TotalVo();
            Integer total1 = agentService.selectByAgentAndHouseType2(agentDto.getBusId(), agentDto.getAgentId());
            log.info("经纪人二手房统计{}", total1);
            if (StringUtils.isEmpty(total1)) {
                totalVo2.setLabel("二手房");
                totalVo2.setTotalCount(0);
            } else {
                totalVo2.setLabel("二手房");
                totalVo2.setTotalCount(total1);
            }

            TotalVo totalVo3 = new TotalVo();
            Integer total2 = agentService.selectByAgentAndHouseType1(agentDto.getBusId(), agentDto.getAgentId());
            log.info("经纪人租房统计{}", total2);
            if (StringUtils.isEmpty(total2)) {
                totalVo3.setLabel("租房");
                totalVo3.setTotalCount(0);
            } else {
                totalVo3.setLabel("租房");
                totalVo3.setTotalCount(total2);
            }

            TotalVo totalVo4 =new TotalVo();
            Integer total3=historyService.selectBySumBrowseCount(agentDto.getBusId(), agentDto.getAgentId());
            log.info("经纪人浏览统计{}", total2);
            if (StringUtils.isEmpty(total3)) {
                totalVo4.setLabel("浏览");
                totalVo4.setTotalCount(0);
            } else {
                totalVo4.setLabel("浏览");
                totalVo4.setTotalCount(total3);
            }
            list.add(totalVo1);
            list.add(totalVo2);
            list.add(totalVo3);
            list.add(totalVo4);

            result.setMessage("统计成功");
            result.setSuccess(true);
            result.setData(list);
            log.info("经纪人房源统计{}", list);
        }
        return result;
    }


    @ApiOperation("查询该经济人下的房源有哪些用户浏览")
    @PostMapping("/api/agent/user/browse")
    public Result queryUserBrowse(@RequestBody AgentUserDto agentDto) {
        Result result = new Result();
        Page page = PageHelper.startPage(agentDto.getPageIndex(), agentDto.getPageSize());
        List<String> histroyList = agentService.selectByUserAndAgent(agentDto.getBusId(), agentDto.getAgentId());
        PageInfo info = new PageInfo<>(page.getResult());
        List list = new ArrayList();
        if (StringUtils.isEmpty(histroyList) || histroyList.size() < 0) {
            Map map = new HashMap();
            map.put("rows", list);
            map.put("pageSize", agentDto.getPageSize());
            map.put("pageIndex", agentDto.getPageIndex());
            map.put("totalCount", info.getTotal());
            result.setMessage("查询成功");
            result.setSuccess(true);
            result.setData(map);
        } else {
            for (String phone : histroyList) {
                if (phone.equals("200")) {
                    continue;
                }
                ReturnUserVO userVO = new ReturnUserVO();
                userVO.setPhone(phone);
                Integer browseCount = agentService.selectByAgentAndUserSum(agentDto.getBusId(), agentDto.getAgentId(), phone);
                if (!StringUtils.isEmpty(browseCount)) {
                    userVO.setRecord(browseCount);
                } else {
                    userVO.setRecord(0);
                }

                Integer collectCount = agentService.selectByAgentAndCollect(agentDto.getBusId(), agentDto.getAgentId(), phone);
                if (StringUtils.isEmpty(collectCount)) {
                    userVO.setFollow(0);
                } else {
                    userVO.setFollow(collectCount);
                }
                list.add(userVO);

            }
            Map map = new HashMap();
            map.put("rows", list);
            map.put("pageSize", agentDto.getPageSize());
            map.put("pageIndex", agentDto.getPageIndex());
            map.put("totalCount", info.getTotal());
            result.setMessage("查询成功");
            result.setSuccess(true);
            result.setData(map);
        }
        return result;
    }


    @ApiOperation("查询用户浏览该经纪人下房源的信息")
    @PostMapping("/api/agent/house/historySelect")
    public Result histroySelect(@RequestBody AgentUserHouseDTO agentUserDTO) {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();

        if (StringUtils.isEmpty(agentUserDTO.getBusId()) || StringUtils.isEmpty(agentUserDTO.getPhone()) || StringUtils.isEmpty(agentUserDTO.getPhone())) {
            result.setSuccess(false);
            result.setMessage("商家权限不足或该用户未授权");
        } else {
            Page page = PageHelper.startPage(agentUserDTO.getPageIndex(), agentUserDTO.getPageSize());
            List<History> historyList = agentService.selectByAgentAndUserAndHouse(agentUserDTO.getBusId(), agentUserDTO.getAgentId(), agentUserDTO.getPhone());
            PageInfo info = new PageInfo(page.getResult());
            List list = new ArrayList();
            if (StringUtils.isEmpty(historyList)) {
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", agentUserDTO.getPageIndex());
                map.put("pageSize", agentUserDTO.getPageSize());
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
                map.put("pageIndex", agentUserDTO.getPageIndex());
                map.put("pageSize", agentUserDTO.getPageSize());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);

            }
        }
        return result;
    }

    @ApiOperation("查询用户关注该经纪人下房源的信息")
    @PostMapping("/api/agent/house/collectSelect")
    public Result collectSelect(@RequestBody AgentUserHouseDTO agentUserDTO) {
        Result result = new Result();
        DateUtils dateUtils = new DateUtils();
        if (StringUtils.isEmpty(agentUserDTO.getBusId()) || StringUtils.isEmpty(agentUserDTO.getPhone()) || StringUtils.isEmpty(agentUserDTO.getPhone())) {
            result.setSuccess(false);
            result.setMessage("用户暂未授权或商家权限不足");
        } else {
            Page page = PageHelper.startPage(agentUserDTO.getPageIndex(), agentUserDTO.getPageSize());
            List<House> houseList = agentService.selectByCollectAndAgent(agentUserDTO.getBusId(), agentUserDTO.getAgentId(), agentUserDTO.getPhone());
            PageInfo info = new PageInfo(page.getResult());
            if (houseList.size() < 0) {
                result.setSuccess(true);
                result.setMessage("该用户暂未关注房源或商家暂无房源被关注");
            } else {
                List list = new ArrayList();
                for (House house : houseList) {
                    Collect collect = new Collect();
                    collect.setHouseId(house.getId());
                    collect.setPhone(agentUserDTO.getPhone());
                    Collect collect2 = collectService.selectByCollectHouse(collect);
                    ReturnHouse house3 = new ReturnHouse();
                    if (!StringUtils.isEmpty(collect2)) {
                        house3.setIsFollow(collect2.getState());
                    } else {
                        house3.setIsFollow(0);
                    }
                    house3.setUserPhone(collect2.getPhone());
                    house3.setCreateTime(collect2.getFollowTime());
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
                    if (list2 != null) {
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
                    History history2 = historyService.selectByDetailCount(house.getId(), agentUserDTO.getPhone());
                    if (!StringUtils.isEmpty(history2.getBrowseCount())) {
                        house3.setRecord(history2.getBrowseCount());
                    } else {
                        house3.setRecord(0);
                    }
                    List<RHouse> rHouseList = houseService.selectByList(house.getId());
                    List list1 = new ArrayList();
                    if (rHouseList != null) {
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
                Map map = new HashMap();
                map.put("rows", list);
                map.put("totalCount", info.getTotal());
                map.put("pageIndex", agentUserDTO.getPageIndex());
                map.put("pageSize", agentUserDTO.getPageSize());
                result.setMessage("查询成功");
                result.setSuccess(true);
                result.setData(map);
            }
        }

        return result;
    }


    @ApiModel
    @Data
    public static class AgentUserHouseDTO {
        private Integer busId;
        private Integer agentId;
        private Integer pageSize;
        private Integer pageIndex;
        private String phone;
    }

    @ApiModel
    @Data
    public static class AgentDto {
        private Integer busId;
        private Integer agentId;
    }

    @ApiModel
    @Data
    public static class AgentUserDto {
        private Integer busId;
        private Integer agentId;
        private Integer pageSize;
        private Integer pageIndex;
    }

    @ApiModel
    @Data
    public static class AgentVO {
        @ApiModelProperty(value = "商户编号", required = true, example = "1")
        private Integer busId;
        private String agentName;
        private String picUrl;
        private String phone;
        private String remark;

    }

    @Data
    public static class BusIdVO {
        @ApiModelProperty(value = "商户编号", required = true, example = "1")
        private Integer busId;
    }

    @Data
    public static class AgentVO2 {
        @ApiModelProperty(value = "商户编号", required = true, example = "1")
        private Integer agentId;
        @ApiModelProperty(example = "1")
        private Integer pageIndex;
        @ApiModelProperty(example = "2")
        private Integer pageSize;
        @ApiModelProperty(value = "类型")
        private Integer type;
    }

    @Data
    public static class AgentIdVO {
        @ApiModelProperty(value = "经纪人编号", required = true, example = "1")
        private Integer id;
    }
}
