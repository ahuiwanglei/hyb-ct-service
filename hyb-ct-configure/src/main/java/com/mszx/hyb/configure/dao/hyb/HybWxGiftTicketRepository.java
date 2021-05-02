package com.mszx.hyb.configure.dao.hyb;

import com.mszx.hyb.configure.model.db.hyb.wx.HybWxBalance;
import com.mszx.hyb.configure.model.db.hyb.wx.HybWxGiftTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HybWxGiftTicketRepository extends JpaRepository<HybWxGiftTicket, String> {
}
