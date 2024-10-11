package org.moguri.stock.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.stock.domain.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {

    void saveTrade(StockTrade trade);

    List<Stock> findStockByKeyword(@Param("pageRequest") PageRequest pageRequest,
                                   @Param("keyword") String keyword);

    int findSearchTotalCount(@Param("keyword") String keyword);

    int findRemainingQuantity(@Param("memberId") Long memberId, @Param("stockCode") String
            stockCode);

    List<TradeHistory> findTradeByStockCode(@Param("pageRequest") PageRequest pageRequest, @Param("memberId") Long memberId, @Param("stockCode") String stockCode);

    int findHistoryTotalCount(@Param("memberId") Long memberId, @Param("stockCode") String stockCode);

    List<UserStock> findAllUserStocks(@Param("memberId") long memberId);

    List<InvestorRanking> findTop10Investors();
}
