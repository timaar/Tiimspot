'use strict';

angular.module('tiimspotApp')
    .factory('PersoonSearch', function ($resource) {
        return $resource('api/_search/persoons/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
