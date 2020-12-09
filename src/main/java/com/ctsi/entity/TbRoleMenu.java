package com.ctsi.entity;

import java.io.Serializable;

/**
 * @author ctsi-biyi-generator
*/
public class TbRoleMenu implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_role_menu.id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_role_menu.role_id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_role_menu.menu_id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    private Integer menuId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_role_menu.id
     *
     * @return the value of tb_role_menu.id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_role_menu.id
     *
     * @param id the value for tb_role_menu.id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_role_menu.role_id
     *
     * @return the value of tb_role_menu.role_id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_role_menu.role_id
     *
     * @param roleId the value for tb_role_menu.role_id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_role_menu.menu_id
     *
     * @return the value of tb_role_menu.menu_id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_role_menu.menu_id
     *
     * @param menuId the value for tb_role_menu.menu_id
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_menu
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TbRoleMenu other = (TbRoleMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_role_menu
     *
     * @mbg.generated Wed Dec 09 15:32:33 CST 2020
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        return result;
    }
}