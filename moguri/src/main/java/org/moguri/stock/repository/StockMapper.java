package org.moguri.stock.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.moguri.common.response.PageRequest;
import org.moguri.stock.domain.StockTrade;
import org.moguri.stock.domain.Stock;
import org.moguri.stock.domain.TradeHistory;

import java.util.List;

@Mapper
public interface StockMapper {

    void saveTrade(StockTrade trade);

    List<Stock> findStockByKeyword(@Param("pageRequest") PageRequest pageRequest,
                                   @Param("keyword") String keyword);

    int getSearchTotalCount(@Param("keyword") String keyword);

    int getRemainingQuantity(@Param("memberId") Long memberId, @Param("stockCode") String
            stockCode);

    List<TradeHistory> findTradeByStockCode(PageRequest pageRequest, Long memberId, String stockCode);

    int getHistoryTotalCount(Long memberId, String stockCode);
}
