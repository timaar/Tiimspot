'use strict';

angular.module('tiimspotApp')
    .factory('OuderSearch', function ($resource) {
        return $resource('api/_search/ouders/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
