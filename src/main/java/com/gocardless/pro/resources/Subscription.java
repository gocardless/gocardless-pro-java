

package com.gocardless.pro.resources;

import java.util.List;

public class Subscription {
    
        
        
            

            private Integer amount;

            public Integer getAmount() {
                return amount;
            }

            public void setAmount(Integer amount) {
                this.amount = amount;
            }
        
    
        
        
            

            private Integer count;

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }
        
    
        
        
            

            private String createdAt;

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }
        
    
        
        
            

            private String currency;

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }
        
    
        
        
            

            private Integer dayOfMonth;

            public Integer getDayOfMonth() {
                return dayOfMonth;
            }

            public void setDayOfMonth(Integer dayOfMonth) {
                this.dayOfMonth = dayOfMonth;
            }
        
    
        
        
            

            private String endAt;

            public String getEndAt() {
                return endAt;
            }

            public void setEndAt(String endAt) {
                this.endAt = endAt;
            }
        
    
        
        
            

            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        
    
        
        
            

            private Integer interval;

            public Integer getInterval() {
                return interval;
            }

            public void setInterval(Integer interval) {
                this.interval = interval;
            }
        
    
        
        
            

            private String intervalUnit;

            public String getIntervalUnit() {
                return intervalUnit;
            }

            public void setIntervalUnit(String intervalUnit) {
                this.intervalUnit = intervalUnit;
            }
        
    
        
        
            

            private Object links;

            public Object getLinks() {
                return links;
            }

            public void setLinks(Object links) {
                this.links = links;
            }
        
    
        
        
            

            private Object metadata;

            public Object getMetadata() {
                return metadata;
            }

            public void setMetadata(Object metadata) {
                this.metadata = metadata;
            }
        
    
        
        
            

            public enum Month {
                
                    JANUARY,
                
                    FEBRUARY,
                
                    MARCH,
                
                    APRIL,
                
                    MAY,
                
                    JUNE,
                
                    JULY,
                
                    AUGUST,
                
                    SEPTEMBER,
                
                    OCTOBER,
                
                    NOVEMBER,
                
                    DECEMBER,
                
            }

            private Month month;

            public Month getMonth() {
                return month;
            }

            public void setMonth(Month month) {
                this.month = month;
            }
        
    
        
        
            

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        
    
        
        
            

            private String startAt;

            public String getStartAt() {
                return startAt;
            }

            public void setStartAt(String startAt) {
                this.startAt = startAt;
            }
        
    
        
        
            

            private String status;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        
    
        
        
            

            private List<Object> upcomingPayments;

            public List<Object> getUpcomingPayments() {
                return upcomingPayments;
            }

            public void setUpcomingPayments(List<Object> upcomingPayments) {
                this.upcomingPayments = upcomingPayments;
            }
        
    
}
