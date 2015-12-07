'use strict';

angular.module('tiimspotApp')
    .factory('AdresSearch', function ($resource) {
        return $resource('api/_search/adress/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
