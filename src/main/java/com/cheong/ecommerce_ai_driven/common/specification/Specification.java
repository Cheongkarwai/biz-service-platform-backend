package com.cheong.ecommerce_ai_driven.common.specification;

public interface Specification <T>{

    boolean isSatisfied(T t);


    default Specification<T> and(Specification<T> specification){
        return condition -> this.isSatisfied(condition) && specification.isSatisfied(condition);
    }

    default Specification<T> or(Specification<T> specification){
        return condition -> this.isSatisfied(condition) || specification.isSatisfied(condition);
    }

    default Specification<T> not(){
        return condition -> !this.isSatisfied(condition);
    }
}
