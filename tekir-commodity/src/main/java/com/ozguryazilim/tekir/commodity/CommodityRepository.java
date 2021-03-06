package com.ozguryazilim.tekir.commodity;

import com.google.common.base.Strings;
import org.apache.deltaspike.data.api.Repository;
import javax.enterprise.context.Dependent;
import com.ozguryazilim.telve.data.RepositoryBase;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;
import com.ozguryazilim.tekir.entities.Commodity;
import com.ozguryazilim.tekir.entities.CommodityCategory;
import com.ozguryazilim.tekir.entities.CommodityCategory_;
import com.ozguryazilim.tekir.entities.TaxDefinition_;
import com.ozguryazilim.tekir.entities.Commodity_;
import com.ozguryazilim.tekir.entities.TaxDefinition;
import com.ozguryazilim.tekir.entities.UnitSetDefinition;
import com.ozguryazilim.tekir.entities.UnitSetDefinition_;
import com.ozguryazilim.telve.query.QueryDefinition;
import com.ozguryazilim.telve.query.columns.BooleanColumn;
import com.ozguryazilim.telve.query.columns.LinkColumn;
import com.ozguryazilim.telve.query.columns.TextColumn;
import com.ozguryazilim.telve.query.filters.Filter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Repository Class
 *
 * @author
 */
@Repository
@Dependent
public abstract class CommodityRepository
extends
RepositoryBase<Commodity, CommodityViewModel>
implements
CriteriaSupport<Commodity> {

	@Override
	public List<CommodityViewModel> browseQuery(QueryDefinition queryDefinition) {
		List<Filter<Commodity, ?, ?>> filters = queryDefinition.getFilters();

		CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
		//Geriye PersonViewModel dönecek cq'yu ona göre oluşturuyoruz.
		CriteriaQuery<CommodityViewModel> criteriaQuery = criteriaBuilder.createQuery(CommodityViewModel.class);

		//From Tabii ki User
		Root<Commodity> from = criteriaQuery.from(Commodity.class);

		//Sonuç filtremiz
		buildVieModelSelect(criteriaQuery, from);
		Join<Commodity, CommodityCategory> cc = from.join(Commodity_.category, JoinType.LEFT);
        Join<Commodity, UnitSetDefinition> us = from.join(Commodity_.unitSet, JoinType.LEFT);
        Join<Commodity, TaxDefinition> t1 = from.join(Commodity_.tax1, JoinType.LEFT);
        Join<Commodity, TaxDefinition> t2 = from.join(Commodity_.tax2, JoinType.LEFT);
        Join<Commodity, TaxDefinition> t3 = from.join(Commodity_.tax3, JoinType.LEFT);


		//Filtreleri ekleyelim.
		List<Predicate> predicates = new ArrayList<>();

		decorateFilters(filters, predicates, criteriaBuilder, from);


		buildSearchTextControl(queryDefinition.getSearchText(),criteriaBuilder, predicates, from);

		//Oluşan filtreleri sorgumuza ekliyoruz
		criteriaQuery.where(predicates.toArray(new Predicate[]{}));

		// Öncelikle default sıralama verelim eğer kullanıcı tarafından tanımlı sıralama var ise onu setleyelim
		if (queryDefinition.getSorters().isEmpty()) {
			criteriaQuery.orderBy(criteriaBuilder.asc(from.get(Commodity_.name)));
		} else {
			criteriaQuery.orderBy(decorateSorts(queryDefinition.getSorters(), criteriaBuilder, from));
		}

		//Haydi bakalım sonuçları alalım
		TypedQuery<CommodityViewModel> typedQuery = entityManager().createQuery(criteriaQuery);
		typedQuery.setMaxResults(queryDefinition.getResultLimit());
		List<CommodityViewModel> resultList = typedQuery.getResultList();

		return resultList;
	}

	@Override
	public List<Commodity> suggestion(String searchText) {
		return criteria()
  				.or(criteria().like(Commodity_.code, "%" + searchText + "%"),
  					criteria().like(Commodity_.name, "%" + searchText + "%"))
 				.eq(Commodity_.active, true).getResultList();	
	}

	private void buildVieModelSelect(CriteriaQuery<CommodityViewModel> criteriaQuery, Root<Commodity> from) {
		criteriaQuery.multiselect(
				from.get(Commodity_.id),
				from.get(Commodity_.code),
				from.get(Commodity_.name),
				from.get(Commodity_.info),
				from.get(Commodity_.active),
				from.get(Commodity_.category).get(CommodityCategory_.id),     
				from.get(Commodity_.category).get(CommodityCategory_.name),     
				from.get(Commodity_.unitSet).get(UnitSetDefinition_.id), 
				from.get(Commodity_.unitSet).get(UnitSetDefinition_.name), 
				from.get(Commodity_.defaultUnit),
				from.get(Commodity_.price),
				from.get(Commodity_.defaultCurrency),	
				from.get(Commodity_.tax1).get(TaxDefinition_.id),
				from.get(Commodity_.tax1).get(TaxDefinition_.name),
				from.get(Commodity_.tax2).get(TaxDefinition_.id),
				from.get(Commodity_.tax2).get(TaxDefinition_.name),
				from.get(Commodity_.tax3).get(TaxDefinition_.id),
				from.get(Commodity_.tax3).get(TaxDefinition_.name)
				);
	}

	private void buildSearchTextControl(String searchText, CriteriaBuilder criteriaBuilder, List<Predicate> predicates, Root<Commodity> from) {
		if (!Strings.isNullOrEmpty(searchText)) {
			predicates.add(criteriaBuilder.or(criteriaBuilder.like(from.get(Commodity_.code), "%" + searchText + "%"),
					criteriaBuilder.like(from.get(Commodity_.name), "%" + searchText + "%")));
		}
	}

	@Override
	public List<CommodityViewModel> lookupQuery(String searchText) {
		CriteriaBuilder criteriaBuilder = entityManager().getCriteriaBuilder();
		//Geriye PersonViewModel dönecek cq'yu ona göre oluşturuyoruz.
		CriteriaQuery<CommodityViewModel> criteriaQuery = criteriaBuilder.createQuery(CommodityViewModel.class);

		//From Tabii ki User
		Root<Commodity> from = criteriaQuery.from(Commodity.class);
		Join<Commodity, CommodityCategory> cc = from.join(Commodity_.category, JoinType.LEFT);
        Join<Commodity, UnitSetDefinition> us = from.join(Commodity_.unitSet, JoinType.LEFT);
        Join<Commodity, TaxDefinition> t1 = from.join(Commodity_.tax1, JoinType.LEFT);
        Join<Commodity, TaxDefinition> t2 = from.join(Commodity_.tax2, JoinType.LEFT);
        Join<Commodity, TaxDefinition> t3 = from.join(Commodity_.tax3, JoinType.LEFT);

		//Sonuç filtremiz
		buildVieModelSelect(criteriaQuery, from );

		//Filtreleri ekleyelim.
		List<Predicate> predicates = new ArrayList<>();

		//Sadece aktif olanlar
		predicates.add(criteriaBuilder.equal(from.get(Commodity_.active), true));

		buildSearchTextControl(searchText, criteriaBuilder, predicates, from);

		//Oluşan filtreleri sorgumuza ekliyoruz
		criteriaQuery.where(predicates.toArray(new Predicate[]{}));

		// Öncelikle default sıralama verelim eğer kullanıcı tarafından tanımlı sıralama var ise onu setleyelim
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get(Commodity_.name)));


		//Haydi bakalım sonuçları alalım
		TypedQuery<CommodityViewModel> typedQuery = entityManager().createQuery(criteriaQuery);
		//Lookuplarda daha fazla sonuç gelmesin
		typedQuery.setMaxResults(50);
		List<CommodityViewModel> resultList = typedQuery.getResultList();

		return resultList;
	}


}
