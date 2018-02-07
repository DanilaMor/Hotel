package com.netcracker.DAO.implementation;

import com.netcracker.DAO.datamodel.AbstractDAO;
import com.netcracker.DAO.datamodel.RoomDAO;
import com.netcracker.DAO.entity.Client;
import com.netcracker.DAO.entity.Room;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 12345 on 18.01.2018.
 */
@Repository
public class RoomDAOImpl extends AbstractDAO implements RoomDAO {

    public RoomDAOImpl() {
    }

    @Override
    public Integer getRoomFree() throws ParseException {
        Integer cout = 0;
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date date = new Date();
        java.sql.Date date1= new java.sql.Date( format.parse(format.format(date)).getTime());
        Query query = getSession().createQuery("SELECT count (room.id_corps) from Room as room left join room.reserv  as res where NOT (res.arrival_date < :date and :date1 < res.date_of_departure) or (res.id is null)");
        query.setParameter("date",date1);
        query.setParameter("date1",date1);
        List list = query.list();
        cout = Integer.parseInt((String) list.get(0).toString());
        return cout;


    }

        @Override
        public List<Room> getListRoom (String date) throws ParseException {
            List<Room> list = null;

                Query query = getSession().createQuery("SELECT new Room(res.id_room,room.id_corps,room.number_of_people,room.floor) from Room as room left join room.reserv  as res where  NOT (res.arrival_date < :date and :date1 < res.date_of_departure) or (res.id is null)");
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("yyyy-MM-dd");
                java.sql.Date date1 = new java.sql.Date(format.parse(date).getTime());
                query.setParameter("date",date1);
                query.setParameter("date1", date1);
                list = (List<Room> ) query.list();
            return list;
        }

    @Override
    public void saveRoom(Room room) {
        try {
            persist(room);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Room> findAllRoom() {
//        Criteria criteria = getSession().createCriteria(Room.class);
//        List<Room> list =(List<Room>) criteria.list();
        List<Room> list = null;

        Query query = getSession().createQuery("SELECT new Room(res.id_room,room.id_corps,room.number_of_people,room.floor) from Room as room join room.reserv  as res");
        list = (List<Room> ) query.list();
        return list;
    }

    @Override
    public Room findRoomById(int id_room, int id_corps) {
        Criteria criteria = getSession().createCriteria(Room.class);
        criteria.add(Restrictions.eq("id_room", id_room));
        criteria.add(Restrictions.eq("id_corps", id_corps));
        return (Room) criteria.uniqueResult();
    }

    @Override
    public int deleteRoomById(int id_room, int id_corp) {
        Query query = getSession().createQuery("DELETE  Room as room\n" +
                " WHERE room.id_room = :id_room and room.id_corps = :id_corps ");
        query.setInteger("id_room", id_room);
        query.setInteger("id_corps", id_corp);
        int n =   query.executeUpdate();
        return n;
    }

    @Override
    public List<Client> certainTime(String data, String data1) throws ParseException {
        Query query = getSession().createQuery("SELECT client from Client as client, Reserv as res where client.login = res.id_client and res.arrival_date > :date and res.date_of_departure < :date1 group by login");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        java.sql.Date date01 = new java.sql.Date(format.parse(data).getTime());
        java.sql.Date date11 = new java.sql.Date(format.parse(data1).getTime());
        query.setParameter("date", date01);
        query.setParameter("date1", date11);

        List<Client> list = (List<Client>) query.list();
        return list;
    }
}

