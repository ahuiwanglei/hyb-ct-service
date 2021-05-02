package com.micro.seller.service.impl;

import com.micro.seller.dao.HybBalanceRepository;
import com.micro.seller.service.VirtualIncomeDataService;
import com.micro.seller.util.TXTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class VirtualIncomeDataServiceImpl implements VirtualIncomeDataService {

    protected static final Logger logger = LoggerFactory.getLogger(VirtualIncomeDataServiceImpl.class);

    @Autowired
    private HybBalanceRepository hybBalanceRepository;

    @Override
    public void getData() {
       List<Object> collection =  hybBalanceRepository.get100Data();
       List<String> wirteList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            Object[] col = (Object[]) collection.get(i);
            Object count = hybBalanceRepository.getSignleUser(col[0].toString());
            BigDecimal virtual = new BigDecimal(col[3].toString());
            Integer cou = Integer.parseInt(count.toString());
            if(virtual.compareTo(new BigDecimal(50000)) >= 0 && cou ==0){
                String a = "手机号：" + col[1] +" 姓名：" + (col[2] == null ? "无" : col[2]) +"  积分数：" + col[3] +"  推荐人数" + (col[4] == null ? 0 : col[4]) +" 推荐注册人发展下线人：" + count;
                logger.info(a);

                wirteList.add(a);

                List<Object> subUsers = hybBalanceRepository.getSubUser(col[0].toString());
                for (int j=0;j<subUsers.size();j++){
                    Object[] col2 = (Object[]) subUsers.get(j);
                    String b = col[1] +"--推荐人--->" + "手机号：" + col2[0] +" 姓名：" + (col2[1] == null ? "无" : col2[1]) ;
                    wirteList.add(b);
                }


            }else{
                logger.info("手机号：" + col[1] +" 姓名：" + (col[2] == null ? "无" : col[2]) +"  积分数：" + col[3] +"  推荐人数" + (col[4] == null ? 0 : col[4]) +" 推荐注册人发展下线人：" + count);
            }

        }

        TXTUtils.writeToTxt(wirteList, "/data/webapps/balance.txt");

    }
}
